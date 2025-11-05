package org.example.websiteenglish.service;

import org.example.websiteenglish.entity.Application;
import java.util.List;

public interface ApplicationService {
    void save(Application application);
    List<Application> findByUserId(int userId);
    boolean userAlreadyAppliedForCourseType(int userId, String courseType);
    boolean userAlreadyAppliedForCourse(int userId, String courseIdentifier);
}
