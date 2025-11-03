package org.example.websiteenglish.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.websiteenglish.dao.impl.ApplicationDaoImpl;
import org.example.websiteenglish.entity.Application;

import java.io.IOException;

@WebServlet("/submitApplication")
public class SubmitApplicationServlet extends HttpServlet {

    private final ApplicationDaoImpl applicationDao = new ApplicationDaoImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");
        String courseTypeRaw = req.getParameter("courseType");
        String studentName = req.getParameter("studentName");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String message = req.getParameter("message");

        // --- преобразуем raw значение в допустимое для БД ---
        String courseType;
        if (courseTypeRaw.startsWith("conversational")) {
            courseType = "CONVERSATIONAL";
        } else if (courseTypeRaw.startsWith("aviation")) {
            courseType = "AVIATION";
        } else {
            System.out.println("Неверный тип курса: " + courseTypeRaw);
            throw new IllegalArgumentException("Неверный тип курса");
        }

        Application app = new Application();
        app.setUserId(userId);
        app.setCourseType(courseType);
        app.setStudentName(studentName);
        app.setEmail(email);
        app.setPhone(phone);
        app.setMessage(message);

        applicationDao.save(app);

        resp.sendRedirect("index.jsp");
    }
}
