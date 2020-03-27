package com.revature.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.User;
import com.revature.repo.UserDAOImpl;

import java.util.List;

public class UserService {
    private static UserService instance = null;
    private static UserDAOImpl userDAO = new UserDAOImpl();
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static UserService getInstance() {
        if (instance == null) instance = new UserService();

        return instance;
    }

    public String getResource(String[] uri) throws JsonProcessingException {
        String response;

        if (uri.length == 1) {
            response = getAllUsers();
        } else {
            // If argument is a number
            if (uri[1].matches("\\d+")) {
                response = getUserById(Integer.parseInt(uri[1]));
            } else {
                response = getUserByUsername(uri[1]);
            }
        }

        return response;
    }

    private String getAllUsers() throws JsonProcessingException {
        List<User> userList = userDAO.getAllUsers();
        return objectMapper.writeValueAsString(userList);
    }

    private String getUserById(int userId) throws JsonProcessingException {
        return objectMapper.writeValueAsString(userDAO.getUserById(userId));
    }

    private String getUserByUsername(String username) throws JsonProcessingException {
        return objectMapper.writeValueAsString(userDAO.getUserByUsername(username));
    }
}
