package org.example.websiteenglish.service;

import org.example.websiteenglish.dto.UserDto;
import org.example.websiteenglish.entity.User;
import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();

    void registerNewUser(String name, String email, String password);
    void updateUser(User user);
    void deleteUser(int id);

    // Проверка пароля при входе
    boolean checkPassword(String email, String password);

    // Проверка, существует ли пользователь с таким email
    boolean emailExists(String email);
    boolean loginExist(String email);

    // Найти пользователя по email
    User findUserByEmail(String email);
    User findByLogin(String email);
}