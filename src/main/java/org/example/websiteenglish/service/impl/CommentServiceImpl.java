package org.example.websiteenglish.service.impl;

import org.example.websiteenglish.entity.Comment;
import org.example.websiteenglish.service.CommentService;
import org.example.websiteenglish.utils.DatabaseConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentServiceImpl implements CommentService {

    @Override
    public void save(int userId, String courseType, String text) throws Exception {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Сообщение пустое");
        }

        if (courseType == null || courseType.isBlank()) {
            throw new IllegalArgumentException("Курс не указан");
        }

        String sql = "INSERT INTO comments (user_id, course_type, text) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setString(2, courseType.toUpperCase());
            stmt.setString(3, text);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Ошибка при сохранении комментария: " + e.getMessage());
        }
    }

    @Override
    public List<Comment> getCommentsByCourse(String courseType) throws Exception {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT c.id, c.user_id, c.text, c.created_at, c.course_type, u.name AS user_name " +
                "FROM comments c JOIN users u ON c.user_id = u.id " +
                "WHERE c.course_type = ? ORDER BY c.created_at ASC";

        try (Connection conn = DatabaseConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, courseType.toUpperCase());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Comment comment = new Comment();
                comment.setId(rs.getInt("id"));
                comment.setUserId(rs.getInt("user_id"));
                comment.setText(rs.getString("text"));
                comment.setCreatedAt(rs.getTimestamp("created_at"));
                comment.setCourseType(rs.getString("course_type"));
                comment.setUserName(rs.getString("user_name"));
                comments.add(comment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Ошибка при загрузке комментариев: " + e.getMessage());
        }

        return comments;
    }

}
