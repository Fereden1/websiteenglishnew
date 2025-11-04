package org.example.websiteenglish.service;

import org.example.websiteenglish.entity.Comment;
import java.util.List;

public interface CommentService {
    void save(int userId, String courseType, String text) throws Exception;

    // новый метод для получения комментариев по курсу
    List<Comment> getCommentsByCourse(String courseType) throws Exception;
}
