package com.revature.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.LoginTemplate;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class LoginServiceTest {
    private LoginTemplate loginTemplate = new LoginTemplate();
    private static LoginService loginService = new LoginService();
    private static ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void tryInvalidLogin() throws IOException {
        loginTemplate.setUsername("bob");
        loginTemplate.setPassword("ross");
        String json = objectMapper.writeValueAsString(loginTemplate);
        assertNull(loginService.getInstance().tryLogin(json));
    }

    @Test
    public void tryValidLogin() throws IOException {
        loginTemplate.setUsername("jean");
        loginTemplate.setPassword("password");
        String json = objectMapper.writeValueAsString(loginTemplate);
        assertNotNull(loginService.getInstance().tryLogin(json));
    }
}