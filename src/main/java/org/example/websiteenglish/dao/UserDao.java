package org.example.websiteenglish.dao;

import org.example.websiteenglish.entity.User;
import java.util.List;

public interface UserDao extends UserCRUD {
    User getByLogin(String login);
    boolean emailExists(String email);
}