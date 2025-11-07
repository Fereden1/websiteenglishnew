package org.example.websiteenglish.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.websiteenglish.entity.Application;
import org.example.websiteenglish.service.ApplicationService;

import java.io.IOException;

@WebServlet("/submitApplication")
public class SubmitApplicationServlet extends BaseServlet {

    private ApplicationService applicationService;
    
    @Override
    public void init() throws ServletException {
        super.init();
        this.applicationService = getServiceContainer().getService(ApplicationService.class);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            resp.sendRedirect("login");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");
        String courseTypeRaw = req.getParameter("courseType");
        String studentName = req.getParameter("studentName");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String message = req.getParameter("message");

        String courseType;
        if (courseTypeRaw.startsWith("conversational")) {
            courseType = "CONVERSATIONAL";
        } else if (courseTypeRaw.startsWith("aviation")) {
            courseType = "AVIATION";
        } else {
            System.out.println("Неверный тип курса: " + courseTypeRaw);
            throw new IllegalArgumentException("Неверный тип курса");
        }

        if (applicationService.userAlreadyAppliedForCourse(userId, courseTypeRaw)) {
            session.setAttribute("errorMessage", "Вы уже отправили заявку на этот курс. Пожалуйста, выберите другой курс.");
            resp.sendRedirect("index");
            return;
        }

        Application app = new Application();
        app.setUserId(userId);
        app.setCourseType(courseType);
        app.setCourseIdentifier(courseTypeRaw);
        app.setStudentName(studentName);
        app.setEmail(email);
        app.setPhone(phone);
        app.setMessage(message);

        applicationService.save(app);

        session.setAttribute("successMessage", "Заявка успешно отправлена!");
        resp.sendRedirect("index");
    }
}
