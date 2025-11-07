package org.example.websiteenglish.service;

import org.example.websiteenglish.dto.UserDto;
import org.example.websiteenglish.entity.User;
import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();

    void registerNewUser(String name, String email, String password);
    void updateUser(User user);
    void deleteUser(int id);

    boolean checkPassword(String email, String password);
    boolean emailExists(String email);
    boolean loginExist(String email);
    User findUserByEmail(String email);
    User findByLogin(String email);
}