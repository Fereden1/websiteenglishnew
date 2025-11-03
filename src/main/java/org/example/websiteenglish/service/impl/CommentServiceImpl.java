package org.example.websiteenglish.service.impl;

import org.example.websiteenglish.dao.CommentDao;
import org.example.websiteenglish.dao.impl.CommentDaoImpl;
import org.example.websiteenglish.entity.Comment;
import org.example.websiteenglish.service.CommentService;

import java.util.List;

public class CommentServiceImpl implements CommentService {

    private final CommentDao commentDao = new CommentDaoImpl();

    @Override
    public void save(int userId, String course, String text) {
        if (course == null || course.isBlank())
            throw new IllegalArgumentException("Курс обязателен");
        if (text == null || text.isBlank())
            throw new IllegalArgumentException("Сообщение пустое");

        Comment comment = new Comment(userId, course, text);
        commentDao.save(comment);
    }

    @Override
    public List<Comment> getByCourse(String course) {
        return commentDao.findByCourse(course);
    }
}
