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

        // Установка кодировки для корректной обработки текста
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/plain; charset=UTF-8");

        // Проверка наличия активной сессии и авторизованного пользователя
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Вы должны войти в систему");
            return;
        }

        // Получение параметров запроса
        int userId = (Integer) session.getAttribute("userId");
        String text = req.getParameter("text");
        String course = req.getParameter("course");

        // Валидация параметров
        if (course == null || course.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Курс не указан");
            return;
        }
        if (text == null || text.trim().isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Сообщение пустое");
            return;
        }

        // Сохранение комментария через сервис
        try {
            commentService.save(userId, course, text);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("OK");
        } catch (IllegalArgumentException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ошибка сервера");
        }
    }
}
