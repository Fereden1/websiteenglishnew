package org.example.websiteenglish.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.websiteenglish.entity.User;
import org.example.websiteenglish.service.UserService;
import org.example.websiteenglish.utils.PasswordUtil;


import java.io.IOException;

@WebServlet(name = "profileServlet", urlPatterns = {"/profile", "/profile/edit", "/profile/delete"})
public class ProfileServlet extends BaseServlet {

    private UserService userService;
    
    @Override
    public void init() throws ServletException {
        super.init();
        this.userService = getServiceContainer().getService(UserService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userEmail") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        String email = (String) session.getAttribute("userEmail");
        User user = userService.findUserByEmail(email);

        String path = req.getServletPath();
        switch (path) {
            case "/profile":
                req.setAttribute("user", user);
                req.getRequestDispatcher("profile.jsp").forward(req, resp);
                break;
            case "/profile/edit":
                req.setAttribute("user", user);
                req.getRequestDispatcher("/editProfile.jsp").forward(req, resp);
                break;

            case "/profile/delete":
                userService.deleteUser(user.getId());
                session.invalidate();
                resp.sendRedirect("index.jsp");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userEmail") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        String email = (String) session.getAttribute("userEmail");

        User currentUser = userService.findUserByEmail(email);

        String name = req.getParameter("name");
        String password = req.getParameter("password");

        currentUser.setName(name);
        if (password != null && !password.isEmpty()) {
            currentUser.setPassword(PasswordUtil.encrypt(password)); // хешируем пароль
        }

        userService.updateUser(currentUser);

        session.setAttribute("userEmail", currentUser.getEmail());

        // вместо редиректа — forward на профиль
        req.setAttribute("user", currentUser);
        req.getRequestDispatcher("/profile.jsp").forward(req, resp);
    }


}
