package org.example.websiteenglish.servlet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import org.example.websiteenglish.container.ServiceContainer;
import org.example.websiteenglish.service.UserService;

@WebServlet(name = "login", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    private UserService userService;
    
    @Override
    public void init() throws ServletException {
        super.init();
        // Получаем контейнер сервисов из ServletContext (инициализирован через WebListener)
        ServletContext context = getServletContext();
        ServiceContainer container = (ServiceContainer) context.getAttribute("serviceContainer");
        if (container != null) {
            this.userService = container.getService(UserService.class);
        } else {
            // Fallback для обратной совместимости
            throw new ServletException("Service container not initialized");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            req.setAttribute("error", "Введите email и пароль");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
            return;
        }

        if (userService.checkPassword(email, password)) {
            var user = userService.findUserByEmail(email);

            HttpSession session = req.getSession(true); // создаём новую, если нет
            session.setAttribute("userEmail", email);
            session.setAttribute("userName", user.getName());
            session.setAttribute("userId", user.getId());
            session.setAttribute("userRole", user.getRole());
            session.setMaxInactiveInterval(60 * 60);

            resp.sendRedirect(req.getContextPath() + "/index.jsp");
        } else {
            req.setAttribute("error", "Неверный email или пароль");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}
