package org.example.websiteenglish.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.websiteenglish.entity.Application;
import org.example.websiteenglish.service.ApplicationService;
import org.example.websiteenglish.service.impl.ApplicationServiceImpl;

import java.io.IOException;

@WebServlet(name = "application", urlPatterns = "/application")
public class ApplicationServlet extends HttpServlet {

    private final ApplicationService applicationService = new ApplicationServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        // Проверка сессии
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            resp.sendRedirect("login.jsp"); // если нет сессии, перенаправляем на логин
            return;
        }

        int userId = (int) session.getAttribute("userId");
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String courseType = req.getParameter("courseType");
        String message = req.getParameter("message");

        // Создаем объект заявки
        Application application = new Application(userId, courseType, name, email, phone, message);

        try {
            // Сохраняем в базу
            applicationService.save(application);
            resp.sendRedirect("profile.jsp"); // после успешной отправки
        } catch (Exception ex) {
            ex.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ошибка при сохранении заявки.");
        }
    }
}
