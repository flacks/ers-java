package com.revature.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.LoginTemplate;
import com.revature.models.User;
import com.revature.repo.UserDAOImpl;
import com.revature.util.PBKDF2Hasher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class LoginService {
    private static LoginService instance = null;
    private static UserDAOImpl userDAO = new UserDAOImpl();
    private static ObjectMapper objectMapper = new ObjectMapper();

    private static Logger logger = LogManager.getLogger(LoginService.class);

    public static LoginService getInstance() {
        if (instance == null) instance = new LoginService();

        return instance;
    }

    public String tryLogin(String json) throws IOException {
        LoginTemplate loginTemplate = objectMapper.readValue(json, LoginTemplate.class);
        User user = userDAO.getUserByUsername(loginTemplate.getUsername());

        if (user != null) {
            PBKDF2Hasher hasher = new PBKDF2Hasher();
            if (hasher.checkPassword(loginTemplate.getPassword().toCharArray(), user.getPassword())) {
                logger.info("Login authorized: " + loginTemplate.getUsername());
                return objectMapper.writeValueAsString(user);
            }
        }

        logger.warn("Login denied: " + loginTemplate.getUsername());
        return null;
    }
}
