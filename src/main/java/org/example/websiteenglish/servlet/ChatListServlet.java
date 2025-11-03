package org.example.websiteenglish.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.websiteenglish.utils.DatabaseConnectionUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/chat/list")
public class ChatListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String course = req.getParameter("course");
        if (course == null || course.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Курс не указан");
            return;
        }

        try (Connection conn = DatabaseConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT c.text, c.created_at, u.name AS user_name " +
                             "FROM comments c JOIN users u ON c.user_id = u.id " +
                             "WHERE c.course = ? ORDER BY c.created_at ASC")) {

            stmt.setString(1, course);
            ResultSet rs = stmt.executeQuery();

            StringBuilder json = new StringBuilder("[");
            boolean first = true;

            while (rs.next()) {
                String name = rs.getString("user_name");
                if (name == null || name.isEmpty()) name = "Аноним";
                String text = rs.getString("text");
                String time = rs.getTimestamp("created_at").toString();

                if (!first) json.append(",");
                first = false;

                json.append("{")
                        .append("\"name\":\"").append(escapeJson(name)).append("\",")
                        .append("\"text\":\"").append(escapeJson(text)).append("\",")
                        .append("\"time\":\"").append(escapeJson(time)).append("\"")
                        .append("}");
            }

            json.append("]");
            resp.getWriter().print(json.toString());

        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendError(500, e.getMessage());
        }
    }

    private String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "")
                .replace("\t", "\\t");
    }
}
