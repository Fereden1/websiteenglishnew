package org.example.websiteenglish.service.impl;

import org.example.websiteenglish.dao.ApplicationDao;
import org.example.websiteenglish.dao.impl.ApplicationDaoImpl;
import org.example.websiteenglish.entity.Application;
import org.example.websiteenglish.service.ApplicationService;

import java.util.List;

public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationDao applicationDao;
    
    // Конструктор для Dependency Injection
    public ApplicationServiceImpl(ApplicationDao applicationDao) {
        this.applicationDao = applicationDao;
    }
    
    // Конструктор по умолчанию для обратной совместимости
    public ApplicationServiceImpl() {
        this.applicationDao = new ApplicationDaoImpl();
    }

    @Override
    public void save(Application application) {
        applicationDao.save(application);
    }

    @Override
    public List<Application> findByUserId(int userId) {
        return applicationDao.findByUserId(userId);
    }

    @Override
    public boolean userAlreadyAppliedForCourseType(int userId, String courseType) {
        return applicationDao.userAlreadyAppliedForCourseType(userId, courseType);
    }

    @Override
    public boolean userAlreadyAppliedForCourse(int userId, String courseIdentifier) {
        return applicationDao.userAlreadyAppliedForCourse(userId, courseIdentifier);
    }
}
