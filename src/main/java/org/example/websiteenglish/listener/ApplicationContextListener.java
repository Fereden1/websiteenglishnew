package org.example.websiteenglish.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.example.websiteenglish.container.ServiceContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * WebListener для инициализации контейнера сервисов при старте приложения
 * Реализует принцип Dependency Injection через ServiceContainer
 */
@WebListener
public class ApplicationContextListener implements ServletContextListener {
    
    private static final Logger logger = LoggerFactory.getLogger(ApplicationContextListener.class);
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        
        logger.info("Initializing application context...");
        
        try {
            // Инициализация контейнера сервисов
            ServiceContainer container = ServiceContainer.getInstance();
            
            // Сохранение контейнера в ServletContext для доступа из сервлетов
            context.setAttribute("serviceContainer", container);
            
            logger.info("Service container initialized successfully");
            logger.info("Registered services: UserService, ApplicationService, CommentService");
            
        } catch (Exception e) {
            logger.error("Failed to initialize service container", e);
            throw new RuntimeException("Application initialization failed", e);
        }
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Application context is being destroyed...");
        // Очистка ресурсов при необходимости
    }
}

