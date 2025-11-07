package org.example.websiteenglish.servlet;

import freemarker.template.TemplateException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.websiteenglish.service.UserService;
import org.example.websiteenglish.utils.FreeMarkerUtil;

import java.io.IOException;
import java.util.Map;

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
        try {
            Map<String, Object> model = FreeMarkerUtil.createDataModel(req);
            FreeMarkerUtil.renderTemplate("register.ftl", model, resp);
        } catch (TemplateException e) {
            throw new ServletException("Error rendering template", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (userService.emailExists(email)) {
            req.setAttribute("error", "Пользователь с таким email уже существует");
            try {
                Map<String, Object> model = FreeMarkerUtil.createDataModel(req);
                FreeMarkerUtil.renderTemplate("register.ftl", model, resp);
            } catch (TemplateException e) {
                throw new ServletException("Error rendering template", e);
            }
        } else {
            userService.registerNewUser(name, email, password);
            resp.sendRedirect("login");
        }
    }
}