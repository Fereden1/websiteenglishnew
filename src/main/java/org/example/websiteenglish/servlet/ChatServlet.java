package org.example.websiteenglish.servlet;

import freemarker.template.TemplateException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.websiteenglish.utils.FreeMarkerUtil;

import java.io.IOException;
import java.util.Map;

@WebServlet(name = "chat", urlPatterns = "/chat")
public class ChatServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Map<String, Object> model = FreeMarkerUtil.createDataModel(req);
            FreeMarkerUtil.renderTemplate("chat.ftl", model, resp);
        } catch (TemplateException e) {
            throw new ServletException("Error rendering template", e);
        }
    }
}

