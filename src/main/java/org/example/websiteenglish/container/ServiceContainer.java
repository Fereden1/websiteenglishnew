package org.example.websiteenglish.container;

import org.example.websiteenglish.dao.ApplicationDao;
import org.example.websiteenglish.dao.CommentDao;
import org.example.websiteenglish.dao.UserDao;
import org.example.websiteenglish.dao.impl.ApplicationDaoImpl;
import org.example.websiteenglish.dao.impl.CommentDaoImpl;
import org.example.websiteenglish.dao.impl.UserDaoImpl;
import org.example.websiteenglish.service.ApplicationService;
import org.example.websiteenglish.service.CommentService;
import org.example.websiteenglish.service.UserService;
import org.example.websiteenglish.service.impl.ApplicationServiceImpl;
import org.example.websiteenglish.service.impl.CommentServiceImpl;
import org.example.websiteenglish.service.impl.UserServiceImpl;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ServiceContainer {
    
    private static ServiceContainer instance;
    private final ConcurrentMap<Class<?>, Object> services = new ConcurrentHashMap<>();
    
    private ServiceContainer() {
        initializeServices();
    }
    
    public static synchronized ServiceContainer getInstance() {
        if (instance == null) {
            instance = new ServiceContainer();
        }
        return instance;
    }
    
    private void initializeServices() {
        UserDao userDao = new UserDaoImpl();
        ApplicationDao applicationDao = new ApplicationDaoImpl();
        CommentDao commentDao = new CommentDaoImpl();
        register(UserDao.class, userDao);
        register(ApplicationDao.class, applicationDao);
        register(CommentDao.class, commentDao);
        UserService userService = new UserServiceImpl(userDao);
        ApplicationService applicationService = new ApplicationServiceImpl(applicationDao);
        CommentService commentService = new CommentServiceImpl(commentDao);
        register(UserService.class, userService);
        register(ApplicationService.class, applicationService);
        register(CommentService.class, commentService);
    }
    
    public <T> void register(Class<T> serviceClass, T service) {
        services.put(serviceClass, service);
    }
    
    @SuppressWarnings("unchecked")
    public <T> T getService(Class<T> serviceClass) {
        Object service = services.get(serviceClass);
        if (service == null) {
            throw new IllegalStateException("Service not found: " + serviceClass.getName());
        }
        return (T) service;
    }
    
    public boolean hasService(Class<?> serviceClass) {
        return services.containsKey(serviceClass);
    }
}

