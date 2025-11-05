package org.example.websiteenglish.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.websiteenglish.service.UserService;

import java.io.IOException;

@WebServlet(name = "register", urlPatterns = "/register")
public class SignUpServlet extends BaseServlet {

    private UserService userService;
    
    @Override
    public void init() throws ServletException {
        super.init();
        this.userService = getServiceContainer().getService(UserService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Показываем страницу регистрации
        req.getRequestDispatcher("/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Получаем данные из формы
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        // Устанавливаем кодировку
        resp.setContentType("text/html; charset=UTF-8");

        // Проверяем, существует ли пользователь с таким email
        if (userService.emailExists(email)) {
            req.setAttribute("error", "Пользователь с таким email уже существует");
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
        } else {
            userService.registerNewUser(name, email, password);
            // ✅ после успешной регистрации — на логин
            resp.sendRedirect("login");
        }

    }
}