package com.revature.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Reimbursement;
import com.revature.models.User;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ReimbursementServiceTest {
    private static ReimbursementService reimbursementService = new ReimbursementService();
    private static UserService userService = new UserService();
    private Reimbursement reimbursement = new Reimbursement();
    private User user = new User();
    private static ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void getInvalidResource() throws JsonProcessingException {
        String[] uriSplit = "/a".split("/");
        assertNull(reimbursementService.getInstance().getResource(uriSplit));
    }

    @Test
    public void getValidResource() throws JsonProcessingException {
        String[] uriSplit = "/1".split("/");
        assertNotNull(reimbursementService.getInstance().getResource(uriSplit));
    }

    @Test
    public void createReimb() throws IOException {
        user = objectMapper.readValue(userService.getInstance().getResource("/3".split("/")), User.class);
        reimbursement.setAuthor(user);
        reimbursement.setDescription("Test");
        reimbursement.setStatusId(1);
        reimbursement.setTypeId(4);
        reimbursement.setAmount(3.50);

        String json = objectMapper.writeValueAsString(reimbursement);
        assertNotNull(reimbursementService.getInstance().createReimb(json));
    }

//    @Test
//    public void updateReimb() throws IOException {
//        // TODO: Fetch pending reimbursements instead of a hardcoded reimbursement
////        reimbursement = objectMapper.readValue(
////                reimbursementService.getInstance().getResource("/19".split("/")), Reimbursement.class);
//        user = objectMapper.readValue(
//                reimbursementService.getInstance().getResource("/3".split("/")), User.class);
//        reimbursement.setResolver(user);
//        reimbursement.setStatusId(3);
//        reimbursement.setTypeId(4);
//
//        String json = objectMapper.writeValueAsString(reimbursement);
//        assertNotNull(reimbursementService.getInstance().updateReimb(json));
//    }
}