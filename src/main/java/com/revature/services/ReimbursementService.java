package com.revature.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Reimbursement;
import com.revature.repo.ReimbursementDAOImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

public class ReimbursementService {
    private static ReimbursementService instance = null;
    private static ReimbursementDAOImpl reimbursementDAO = new ReimbursementDAOImpl();
    private static ObjectMapper objectMapper = new ObjectMapper();

    private static Logger logger = LogManager.getLogger(ReimbursementService.class);

    public static ReimbursementService getInstance() {
        if (instance == null) instance = new ReimbursementService();

        return instance;
    }

    public String getResource(String[] uri) throws JsonProcessingException {
        String response = null;

        if (uri.length == 1) {
            logger.info("Fetching all reimbursements");
            response = getAllReimbs();
        } else {
            // If argument is a number
            if (uri[1].matches("\\d+")) {
                logger.info("Fetching reimbursements by user ID: #" + uri[1]);
                response = getAllReimbsByUserId(Integer.parseInt(uri[1]));
            }
        }

        if (response == null) logger.warn("Requested resource " + uri[1] + " does not exist");

        return response;
    }

    private String getReimbById(int reimbId) throws JsonProcessingException {
        return objectMapper.writeValueAsString(reimbursementDAO.getReimbById(reimbId));
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
