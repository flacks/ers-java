package com.revature.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Reimbursement;
import com.revature.repo.ReimbursementDAOImpl;

import java.io.IOException;
import java.util.List;

public class ReimbursementService {
    private static ReimbursementService instance = null;
    private static ReimbursementDAOImpl reimbursementDAO = new ReimbursementDAOImpl();
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static ReimbursementService getInstance() {
        if (instance == null) instance = new ReimbursementService();

        return instance;
    }

    public String getResource(String[] uri) throws JsonProcessingException {
        String response = null;

        if (uri.length == 1) {
            response = getAllReimbs();
        } else {
            // If argument is a number
            if (uri[1].matches("\\d+")) {
                response = getAllReimbsByUserId(Integer.parseInt(uri[1]));
            }
        }

        return response;
    }

    private String getAllReimbs() throws JsonProcessingException {
        List<Reimbursement> reimbursementList = reimbursementDAO.getAllReimbs();
        return objectMapper.writeValueAsString(reimbursementList);
    }

    private String getAllReimbsByUserId(int userId) throws JsonProcessingException {
        return objectMapper.writeValueAsString(reimbursementDAO.getAllReimbsByUserId(userId));
    }

    public boolean createReimb(String json) throws IOException {
        Reimbursement reimbursement = objectMapper.readValue(json, Reimbursement.class);
        return reimbursementDAO.createReimb(reimbursement);
    }

    public boolean updateReimb(String json) throws IOException {
        Reimbursement reimbursement = objectMapper.readValue(json, Reimbursement.class);
        return reimbursementDAO.updateReimb(reimbursement);
    }
}
