package org.example.websiteenglish.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.websiteenglish.entity.Application;
import org.example.websiteenglish.service.ApplicationService;

import java.io.IOException;

@WebServlet(name = "application", urlPatterns = "/application")
public class ApplicationServlet extends BaseServlet {

    private ApplicationService applicationService;
    
    @Override
    public void init() throws ServletException {
        super.init();
        this.applicationService = getServiceContainer().getService(ApplicationService.class);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            resp.sendRedirect("login");
            return;
        }

        int userId = (int) session.getAttribute("userId");
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String courseTypeRaw = req.getParameter("courseType");
        String message = req.getParameter("message");

        String courseType;
        String courseIdentifier;
        
        if (courseTypeRaw != null && (courseTypeRaw.contains("-") || courseTypeRaw.equals("conversational-basic") || 
                courseTypeRaw.equals("conversational-advanced") || courseTypeRaw.equals("aviation-pilots") || 
                courseTypeRaw.equals("aviation-dispatchers"))) {
            courseIdentifier = courseTypeRaw;
            if (courseTypeRaw.startsWith("conversational")) {
                courseType = "CONVERSATIONAL";
            } else if (courseTypeRaw.startsWith("aviation")) {
                courseType = "AVIATION";
            } else {
                courseType = courseTypeRaw;
            }
        } else {
            courseType = courseTypeRaw;
            courseIdentifier = courseTypeRaw;
        }

        if (applicationService.userAlreadyAppliedForCourse(userId, courseIdentifier)) {
            session.setAttribute("errorMessage", "Вы уже отправили заявку на этот курс. Пожалуйста, выберите другой курс.");
            resp.sendRedirect("profile");
            return;
        }

        Application application = new Application(userId, courseType, name, email, phone, message);
        application.setCourseIdentifier(courseIdentifier);

        try {
            applicationService.save(application);
            session.setAttribute("successMessage", "Заявка успешно отправлена!");
            resp.sendRedirect("profile");
        } catch (Exception ex) {
            ex.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ошибка при сохранении заявки.");
        }
    }
}
