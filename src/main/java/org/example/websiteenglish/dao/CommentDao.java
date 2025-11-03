package org.example.websiteenglish.dao;

import org.example.websiteenglish.entity.Comment;
import java.util.List;

public interface CommentDao {
    void save(Comment comment);
    List<Comment> findByCourse(String course);
}
