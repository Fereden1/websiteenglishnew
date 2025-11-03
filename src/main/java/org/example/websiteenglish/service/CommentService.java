package org.example.websiteenglish.service;

import org.example.websiteenglish.entity.Comment;
import java.util.List;

public interface CommentService {
    void save(int userId, String course, String text);
    List<Comment> getByCourse(String course);
}
