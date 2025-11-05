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

/**
 * Контейнер для управления зависимостями (Dependency Injection)
 * Реализует паттерн Singleton для обеспечения единственного экземпляра
 */
public class ServiceContainer {
    
    private static ServiceContainer instance;
    private final ConcurrentMap<Class<?>, Object> services = new ConcurrentHashMap<>();
    
    private ServiceContainer() {
        initializeServices();
    }
    
    /**
     * Получить единственный экземпляр контейнера (Singleton)
     */
    public static synchronized ServiceContainer getInstance() {
        if (instance == null) {
            instance = new ServiceContainer();
        }
        return instance;
    }
    
    /**
     * Создание всех сервисов и DAO при старте приложения
     */
    private void initializeServices() {
        // DAO слои
        UserDao userDao = new UserDaoImpl();
        ApplicationDao applicationDao = new ApplicationDaoImpl();
        CommentDao commentDao = new CommentDaoImpl();
        
        // Регистрируем DAO
        register(UserDao.class, userDao);
        register(ApplicationDao.class, applicationDao);
        register(CommentDao.class, commentDao);
        
        // Service слои с инъекцией зависимостей
        UserService userService = new UserServiceImpl(userDao);
        ApplicationService applicationService = new ApplicationServiceImpl(applicationDao);
        CommentService commentService = new CommentServiceImpl(commentDao);
        
        // Регистрируем сервисы
        register(UserService.class, userService);
        register(ApplicationService.class, applicationService);
        register(CommentService.class, commentService);
    }
    
    /**
     * Добавить сервис в контейнер
     */
    public <T> void register(Class<T> serviceClass, T service) {
        services.put(serviceClass, service);
    }
    
    /**
     * Получить сервис из контейнера
     * @param serviceClass класс сервиса
     * @return экземпляр сервиса
     * @throws IllegalStateException если сервис не найден
     */
    @SuppressWarnings("unchecked")
    public <T> T getService(Class<T> serviceClass) {
        Object service = services.get(serviceClass);
        if (service == null) {
            throw new IllegalStateException("Service not found: " + serviceClass.getName());
        }
        return (T) service;
    }
    
    /**
     * Проверить, есть ли сервис в контейнере
     */
    public boolean hasService(Class<?> serviceClass) {
        return services.containsKey(serviceClass);
    }
}

