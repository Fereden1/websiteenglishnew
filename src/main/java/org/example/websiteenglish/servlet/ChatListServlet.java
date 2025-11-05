package org.example.websiteenglish.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.websiteenglish.entity.Comment;
import org.example.websiteenglish.service.CommentService;

import java.io.IOException;
import java.util.List;

@WebServlet("/chat/list")
public class ChatListServlet extends BaseServlet {

    private CommentService commentService;
    
    @Override
    public void init() throws ServletException {
        super.init();
        this.commentService = getServiceContainer().getService(CommentService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String courseType = req.getParameter("course");
        if (courseType == null || courseType.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Курс не указан");
            return;
        }

        try {
            List<Comment> comments = commentService.getAllCommentsForCourse(courseType);

            StringBuilder json = new StringBuilder("[");
            boolean first = true;
            for (Comment c : comments) {
                if (!first) json.append(",");
                first = false;
                json.append("{")
                        .append("\"name\":\"").append(escapeJson(c.getUserName())).append("\",")
                        .append("\"text\":\"").append(escapeJson(c.getText())).append("\",")
                        .append("\"time\":\"").append(c.getCreatedAt().toString()).append("\"")
                        .append("}");
            }
            json.append("]");
            resp.getWriter().write(json.toString());

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ошибка сервера");
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
