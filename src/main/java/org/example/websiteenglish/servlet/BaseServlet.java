package org.example.websiteenglish.servlet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import org.example.websiteenglish.container.ServiceContainer;

/**
 * Базовый класс для сервлетов с поддержкой Dependency Injection
 * Упрощает получение сервисов из контейнера
 */
public abstract class BaseServlet extends HttpServlet {
    
    protected ServiceContainer getServiceContainer() throws ServletException {
        ServletContext context = getServletContext();
        ServiceContainer container = (ServiceContainer) context.getAttribute("serviceContainer");
        if (container == null) {
            throw new ServletException("Service container not initialized. Check ApplicationContextListener.");
        }
        return container;
    }
}

