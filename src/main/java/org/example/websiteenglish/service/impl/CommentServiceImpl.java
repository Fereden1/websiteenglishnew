package org.example.websiteenglish.service.impl;

import org.example.websiteenglish.dao.CommentDao;
import org.example.websiteenglish.dao.impl.CommentDaoImpl;
import org.example.websiteenglish.entity.Comment;
import org.example.websiteenglish.service.CommentService;

import java.util.List;

public class CommentServiceImpl implements CommentService {

    private final CommentDao commentDao;
    
    public CommentServiceImpl(CommentDao commentDao) {
        this.commentDao = commentDao;
    }
    
    public CommentServiceImpl() {
        this.commentDao = new CommentDaoImpl();
    }

    @Override
    public void save(int userId, String courseType, String text) throws Exception {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Сообщение пустое");
        }

        if (courseType == null || courseType.isBlank()) {
            throw new IllegalArgumentException("Курс не указан");
        }

        Comment comment = new Comment();
        comment.setUserId(userId);
        comment.setCourseType(courseType.toUpperCase());
        comment.setText(text);
        
        commentDao.save(comment);
    }

    @Override
    public List<Comment> getAllCommentsForCourse(String courseType) throws Exception {
        if (courseType == null || courseType.isBlank()) {
            throw new IllegalArgumentException("Курс не указан");
        }
        
        return commentDao.findByCourse(courseType.toUpperCase());
    }

}
