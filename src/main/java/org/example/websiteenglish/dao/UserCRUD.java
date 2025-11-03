package org.example.websiteenglish.dao;

import org.example.websiteenglish.entity.User;
import java.util.List;

public interface UserCRUD {

    List<User> getAll();

    void save(User user);

    User getById(Integer id);

    void update(User user);

    void delete(int id);
}
