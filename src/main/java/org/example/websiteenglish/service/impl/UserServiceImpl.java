package org.example.websiteenglish.service.impl;

import org.example.websiteenglish.dao.UserDao;
import org.example.websiteenglish.dao.impl.UserDaoImpl;
import org.example.websiteenglish.dto.UserDto;
import org.example.websiteenglish.entity.User;
import org.example.websiteenglish.service.UserService;
import org.example.websiteenglish.utils.PasswordUtil;

import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    private final UserDao userDao = new UserDaoImpl();

    @Override
    public List<UserDto> getAll() {
        return userDao.getAll().stream()
                .map(u -> new UserDto(u.getEmail(), u.getName()))
                .collect(Collectors.toList());
    }

    // ------------------ НОВЫЙ метод signUp ------------------
    public void signUp(String name, String email, String password) {
        String encryptedPassword = PasswordUtil.encrypt(password);
        User user = new User(email, encryptedPassword, name);
        userDao.save(user);
    }
    // ---------------------------------------------------------


    @Override
    public User findByLogin(String email) {
        return userDao.getByLogin(email);
    }

    @Override
    public boolean loginExist(String email) {
        return userDao.getByLogin(email) != null;
    }

    @Override
    public boolean authenticate(String email, String password) {
        User user = userDao.getByLogin(email);
        if (user == null) return false;
        String encryptedPassword = PasswordUtil.encrypt(password);
        return user.getPassword().equals(encryptedPassword);
    }

    @Override
    public User getByEmail(String email) {
        return userDao.getByLogin(email);
    }

    @Override
    public boolean emailExists(String email) {
        return userDao.getByLogin(email) != null;
    }

    @Override
    public void updateUser(User user) {
        userDao.update(user);
    }

    @Override
    public void deleteUser(int id) {
        userDao.delete(id);
    }

}