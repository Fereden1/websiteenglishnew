package org.example.websiteenglish.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class FreeMarkerUtil {
    
    private static Configuration cfg;
    
    public static void init(ServletContext servletContext) {
        cfg = new Configuration(Configuration.VERSION_2_3_34);
        cfg.setServletContextForTemplateLoading(servletContext, "/WEB-INF/templates");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
    }
    
    public static void renderTemplate(String templateName, 
                                     Map<String, Object> dataModel,
                                     HttpServletResponse response) throws IOException, TemplateException {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        Template template = cfg.getTemplate(templateName);
        Writer out = response.getWriter();
        template.process(dataModel, out);
    }
    
    public static Map<String, Object> createDataModel(HttpServletRequest request) {
        Map<String, Object> model = new HashMap<>();
        if (request.getAttribute("user") != null) {
            model.put("user", request.getAttribute("user"));
        }
        if (request.getAttribute("error") != null) {
            model.put("error", request.getAttribute("error"));
        }
        if (request.getSession(false) != null) {
            var session = request.getSession(false);
            model.put("userName", session.getAttribute("userName"));
            model.put("userEmail", session.getAttribute("userEmail"));
            model.put("userRole", session.getAttribute("userRole"));
            model.put("userId", session.getAttribute("userId"));
            Object errorMessage = session.getAttribute("errorMessage");
            Object successMessage = session.getAttribute("successMessage");
            model.put("errorMessage", errorMessage);
            model.put("successMessage", successMessage);
            if (errorMessage != null) {
                session.removeAttribute("errorMessage");
            }
            if (successMessage != null) {
                session.removeAttribute("successMessage");
            }
        }
        
        return model;
    }
}

