package org.example.websiteenglish.dao.impl;

import org.example.websiteenglish.dao.ApplicationDao;
import org.example.websiteenglish.entity.Application;
import org.example.websiteenglish.utils.DatabaseConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApplicationDaoImpl implements ApplicationDao {

    private final Connection connection;

    public ApplicationDaoImpl() {
        this.connection = DatabaseConnectionUtil.getConnection();
    }

    @Override
    public void save(Application application) {
        String sql = "INSERT INTO applications (user_id, course_type, student_name, email, phone, message) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, application.getUserId());
            stmt.setString(2, application.getCourseType());
            stmt.setString(3, application.getStudentName());
            stmt.setString(4, application.getEmail());
            stmt.setString(5, application.getPhone());
            stmt.setString(6, application.getMessage());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Application> findByUserId(int userId) {
        List<Application> applications = new ArrayList<>();
        String sql = "SELECT * FROM applications WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Application app = new Application();
                app.setId(rs.getInt("id"));
                app.setUserId(rs.getInt("user_id"));
                app.setCourseType(rs.getString("course_type"));
                app.setStudentName(rs.getString("student_name"));
                app.setEmail(rs.getString("email"));
                app.setPhone(rs.getString("phone"));
                app.setMessage(rs.getString("message"));
                app.setStatus(rs.getString("status"));
                applications.add(app);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return applications;
    }
}
