package org.example.websiteenglish.service;

import org.example.websiteenglish.dto.UserDto;
import org.example.websiteenglish.entity.User;
import java.util.List;

public interface UserService {
    List<UserDto> getAll();

    void signUp(String name, String email, String password); // если без картинки
    // или, если с Cloudinary:
    // void signUp(String name, String email, String password, Part imagePart) throws IOException;
    void updateUser(User user);
    void deleteUser(int id);


    // Аутентификация
    boolean authenticate(String email, String password);

    // Проверка, существует ли логин/email
    boolean loginExist(String email);
    boolean emailExists(String email);

    // Получение пользователя по email
    User findByLogin(String email);
    User getByEmail(String email);
}