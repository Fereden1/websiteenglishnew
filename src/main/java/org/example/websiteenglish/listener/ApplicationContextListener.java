package org.example.websiteenglish.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.example.websiteenglish.container.ServiceContainer;
import org.example.websiteenglish.utils.DatabaseConnectionUtil;
import org.example.websiteenglish.utils.FreeMarkerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebListener
public class ApplicationContextListener implements ServletContextListener {
    
    private static final Logger logger = LoggerFactory.getLogger(ApplicationContextListener.class);
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        
        try {
            ServiceContainer container = ServiceContainer.getInstance();
            context.setAttribute("serviceContainer", container);
            logger.info("Service container initialized successfully");
            logger.info("Registered services: UserService, ApplicationService, CommentService");
            FreeMarkerUtil.init(context);
            logger.info("FreeMarker initialized successfully");
            
        } catch (Exception e) {
            logger.error("Failed to initialize application context", e);
            throw new RuntimeException("Application initialization failed", e);
        }
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Application context is being destroyed...");
        try {
            DatabaseConnectionUtil.closeAllConnections();
            logger.info("All database connections closed");
        } catch (Exception e) {
            logger.warn("Failed to close some database connections", e);
        }
    }
}

