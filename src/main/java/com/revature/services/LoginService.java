package com.revature.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.LoginTemplate;
import com.revature.models.User;
import com.revature.repo.UserDAOImpl;
import com.revature.util.PBKDF2Hasher;

import java.io.IOException;

public class LoginService {
    private static LoginService instance = null;
    private static UserDAOImpl userDAO = new UserDAOImpl();
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static LoginService getInstance() {
        if (instance == null) instance = new LoginService();

        return instance;
    }

    public String tryLogin(String json) throws IOException {
        LoginTemplate template = objectMapper.readValue(json, LoginTemplate.class);
        User user = userDAO.getUserByUsername(template.getUsername());

        PBKDF2Hasher hasher = new PBKDF2Hasher();
        if (hasher.checkPassword(template.getPassword().toCharArray(), user.getPassword())) {
            return objectMapper.writeValueAsString(user);
        }

        return null;
    }
}
