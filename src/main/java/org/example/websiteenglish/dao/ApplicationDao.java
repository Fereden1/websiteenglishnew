package org.example.websiteenglish.dao;

import org.example.websiteenglish.entity.Application;
import java.util.List;

public interface ApplicationDao {
    void save(Application application);
    List<Application> findByUserId(int userId);
}
