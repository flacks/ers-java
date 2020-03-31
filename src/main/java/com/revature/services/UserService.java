package com.revature.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.User;
import com.revature.repo.UserDAOImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class UserService {
    private static UserService instance = null;
    private static UserDAOImpl userDAO = new UserDAOImpl();
    private static ObjectMapper objectMapper = new ObjectMapper();

    private static Logger logger = LogManager.getLogger(UserService.class);

    public static UserService getInstance() {
        if (instance == null) instance = new UserService();

        return instance;
    }

    public String getResource(String[] uri) throws JsonProcessingException {
        String response;

        if (uri.length == 1) {
            logger.info("Fetching all users");
            response = getAllUsers();
        } else {
            // If argument is a number
            if (uri[1].matches("\\d+")) {
                logger.info("Fetching specific user: ID #" + uri[1]);
                response = getUserById(Integer.parseInt(uri[1]));
            } else {
                logger.info("Fetching specific user by username: " + uri[1]);
                response = getUserByUsername(uri[1]);
            }
        }

        if (response == null) logger.warn("Resource does not exist");

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
