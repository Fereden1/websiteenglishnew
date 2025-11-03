package org.example.websiteenglish.service.impl;

import org.example.websiteenglish.dao.ApplicationDao;
import org.example.websiteenglish.dao.impl.ApplicationDaoImpl;
import org.example.websiteenglish.entity.Application;
import org.example.websiteenglish.service.ApplicationService;

import java.util.List;

public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationDao applicationDao = new ApplicationDaoImpl();

    @Override
    public void save(Application application) {
        applicationDao.save(application);
    }

    @Override
    public List<Application> findByUserId(int userId) {
        return applicationDao.findByUserId(userId);
    }
}
