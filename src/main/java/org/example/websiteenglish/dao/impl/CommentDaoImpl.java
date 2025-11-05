package org.example.websiteenglish.dao.impl;

import org.example.websiteenglish.dao.CommentDao;
import org.example.websiteenglish.entity.Comment;
import org.example.websiteenglish.utils.DatabaseConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentDaoImpl implements CommentDao {

    @Override
    public void save(Comment comment) {
        String sql = "INSERT INTO comments (user_id, course_type, text) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, comment.getUserId());
            stmt.setString(2, comment.getCourseType()); // <-- исправлено
            stmt.setString(3, comment.getText());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) comment.setId(rs.getInt(1));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Comment> findByCourse(String courseType) {
        List<Comment> comments = new ArrayList<>();
        String sql = """
            SELECT c.id, c.user_id, c.text, c.created_at, c.course_type, u.name AS user_name
            FROM comments c
            JOIN users u ON c.user_id = u.id
            WHERE c.course_type = ?
            ORDER BY c.created_at ASC
        """;
        try (Connection conn = DatabaseConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, courseType);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Comment c = new Comment();
                c.setId(rs.getInt("id"));
                c.setUserId(rs.getInt("user_id"));
                c.setText(rs.getString("text"));
                c.setCreatedAt(rs.getTimestamp("created_at"));
                c.setCourseType(rs.getString("course_type"));
                c.setUserName(rs.getString("user_name"));
                comments.add(c);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return comments;
    }
}
