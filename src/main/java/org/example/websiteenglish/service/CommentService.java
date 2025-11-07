package org.example.websiteenglish.service;

import org.example.websiteenglish.entity.Comment;
import java.util.List;

public interface CommentService {
    void save(int userId, String courseType, String text) throws Exception;

    List<Comment> getAllCommentsForCourse(String courseType) throws Exception;
}
