package com.revature.repo;

import com.revature.models.User;

import java.util.List;

public interface UserDAO {
    User getUserById(int userId);

    User getUserByUsername(String username);

    List<User> getAllUsers();
}
