package com.revature.repo;

import com.revature.models.Reimbursement;
import com.revature.models.User;

import java.util.List;

public interface ReimbursementDAO {
    public Reimbursement getReimbById(int reimbId);

    public List<Reimbursement> getAllReimbsByUserId(int userId);

    public List<Reimbursement> getAllReimbs();

    public boolean createReimb(Reimbursement reimbursement);

    public boolean updateReimb(Reimbursement reimbursement);

//    public boolean denyReimb(int reimbId);
}
