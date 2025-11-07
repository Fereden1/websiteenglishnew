package org.example.websiteenglish.servlet;

import freemarker.template.TemplateException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.websiteenglish.entity.User;
import org.example.websiteenglish.service.UserService;
import org.example.websiteenglish.utils.FreeMarkerUtil;
import org.example.websiteenglish.utils.PasswordUtil;

import java.io.IOException;
import java.util.Map;

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
            resp.sendRedirect("login");
            return;
        }

        String email = (String) session.getAttribute("userEmail");
        User user = userService.findUserByEmail(email);

        String path = req.getServletPath();
        try {
            Map<String, Object> model = FreeMarkerUtil.createDataModel(req);
            model.put("user", user);
            
            switch (path) {
                case "/profile":
                    FreeMarkerUtil.renderTemplate("profile.ftl", model, resp);
                    break;
                case "/profile/edit":
                    FreeMarkerUtil.renderTemplate("editProfile.ftl", model, resp);
                    break;
                case "/profile/delete":
                    userService.deleteUser(user.getId());
                    session.invalidate();
                    resp.sendRedirect("index");
                    break;
            }
        } catch (TemplateException e) {
            throw new ServletException("Error rendering template", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userEmail") == null) {
            resp.sendRedirect("login");
            return;
        }

        String email = (String) session.getAttribute("userEmail");
        User currentUser = userService.findUserByEmail(email);

        String name = req.getParameter("name");
        String password = req.getParameter("password");

        currentUser.setName(name);
        if (password != null && !password.isEmpty()) {
            currentUser.setPassword(PasswordUtil.encrypt(password));
        }

        userService.updateUser(currentUser);
        session.setAttribute("userEmail", currentUser.getEmail());

        try {
            Map<String, Object> model = FreeMarkerUtil.createDataModel(req);
            model.put("user", currentUser);
            FreeMarkerUtil.renderTemplate("profile.ftl", model, resp);
        } catch (TemplateException e) {
            throw new ServletException("Error rendering template", e);
        }
    }


}
