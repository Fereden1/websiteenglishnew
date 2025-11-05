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
        String courseTypeRaw = req.getParameter("courseType");
        String message = req.getParameter("message");

        // Определяем, является ли courseTypeRaw конкретным идентификатором или обобщенным типом
        String courseType;
        String courseIdentifier;
        
        if (courseTypeRaw != null && (courseTypeRaw.contains("-") || courseTypeRaw.equals("conversational-basic") || 
                courseTypeRaw.equals("conversational-advanced") || courseTypeRaw.equals("aviation-pilots") || 
                courseTypeRaw.equals("aviation-dispatchers"))) {
            // Это конкретный идентификатор курса
            courseIdentifier = courseTypeRaw;
            // Преобразуем в обобщенный тип для БД
            if (courseTypeRaw.startsWith("conversational")) {
                courseType = "CONVERSATIONAL";
            } else if (courseTypeRaw.startsWith("aviation")) {
                courseType = "AVIATION";
            } else {
                courseType = courseTypeRaw; // Оставляем как есть, если не распознано
            }
        } else {
            // Это обобщенный тип (CONVERSATIONAL или AVIATION)
            courseType = courseTypeRaw;
            courseIdentifier = courseTypeRaw; // Используем как идентификатор
        }

        // Проверка на дубликат заявки по конкретному идентификатору
        if (applicationService.userAlreadyAppliedForCourse(userId, courseIdentifier)) {
            session.setAttribute("errorMessage", "Вы уже отправили заявку на этот курс. Пожалуйста, выберите другой курс.");
            resp.sendRedirect("profile.jsp");
            return;
        }

        // Создаем объект заявки
        Application application = new Application(userId, courseType, name, email, phone, message);
        application.setCourseIdentifier(courseIdentifier); // Устанавливаем конкретный идентификатор курса

        try {
            // Сохраняем в базу
            applicationService.save(application);
            session.setAttribute("successMessage", "Заявка успешно отправлена!");
            resp.sendRedirect("profile.jsp"); // после успешной отправки
        } catch (Exception ex) {
            ex.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ошибка при сохранении заявки.");
        }
    }
}
