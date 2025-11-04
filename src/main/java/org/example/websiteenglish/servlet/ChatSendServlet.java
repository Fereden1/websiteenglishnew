package org.example.websiteenglish.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.websiteenglish.service.CommentService;
import org.example.websiteenglish.service.impl.CommentServiceImpl;

import java.io.IOException;

@WebServlet("/chat/send")
public class ChatSendServlet extends HttpServlet {

    private final CommentService commentService = new CommentServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/plain; charset=UTF-8");

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Вы должны войти в систему");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");
        String text = req.getParameter("text");
        String courseType = req.getParameter("course");

        if (courseType == null || courseType.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Курс не указан");
            return;
        }
        if (text == null || text.trim().isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Комментарий пустой");
            return;
        }

        try {
            commentService.save(userId, courseType, text);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("OK");
        } catch (IllegalArgumentException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ошибка сервера");
        }
    }
}
