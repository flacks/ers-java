package com.revature.repo;

import com.revature.models.Reimbursement;
import com.revature.util.ConnectionUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReimbursementDAOImpl implements ReimbursementDAO {
    private static UserDAOImpl userDAO = new UserDAOImpl();

    @Override
    public Reimbursement getReimbById(int reimbId) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM ers_reimbursement WHERE reimb_id = ?";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, reimbId);

            ResultSet resultset = statement.executeQuery();

            if (resultset.next()) {
                double amount = resultset.getDouble("reimb_amount");
                Timestamp submitted = resultset.getTimestamp("reimb_submitted");
                Timestamp resolved = resultset.getTimestamp("reimb_resolved");
                String description = resultset.getString("reimb_description");
                int author = resultset.getInt("reimb_author");
                int resolver = resultset.getInt("reimb_resolver");
                int statusId = resultset.getInt("reimb_status_id");
                int typeId = resultset.getInt("reimb_type_id");

                Blob receiptBlob = resultset.getBlob("reimb_receipt");
                String receipt = null;
                if (receiptBlob != null) {
                    StringBuffer receiptBuffer = new StringBuffer();
                    String line;
                    BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(receiptBlob.getBinaryStream())
                    );
                    while ((line = bufferedReader.readLine()) != null) {
                        receiptBuffer.append(line);
                    }

                    receipt = receiptBuffer.toString();
                }

                Reimbursement reimbursement = new Reimbursement(
                        reimbId,
                        amount,
                        submitted,
                        resolved,
                        description,
                        receipt,
                        userDAO.getUserById(author),
                        userDAO.getUserById(resolver),
//                        Status.values()[statusId - 1],
                        statusId,
//                        Type.values()[typeId - 1]
                        typeId
                );

                return reimbursement;
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Reimbursement> getAllReimbsByUserId(int userId) {
        List<Reimbursement> reimbursementList = new ArrayList<>();

        try (Connection connection = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM ers_reimbursement " +
                    "JOIN ers_users ON ers_reimbursement.reimb_author = ers_users.ers_users_id " +
                    "WHERE ers_users_id = ? " +
                    "ORDER BY ers_reimbursement.reimb_id";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, userId);

            ResultSet resultset = statement.executeQuery();

            while (resultset.next()) {
                int reimbId = resultset.getInt("reimb_id");
                double amount = resultset.getDouble("reimb_amount");
                Timestamp submitted = resultset.getTimestamp("reimb_submitted");
                Timestamp resolved = resultset.getTimestamp("reimb_resolved");
                String description = resultset.getString("reimb_description");
                int resolver = resultset.getInt("reimb_resolver");
                int statusId = resultset.getInt("reimb_status_id");
                int typeId = resultset.getInt("reimb_type_id");

                Blob receiptBlob = resultset.getBlob("reimb_receipt");
                String receipt = null;
                if (receiptBlob != null) {
                    StringBuffer receiptBuffer = new StringBuffer();
                    String line;
                    BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(receiptBlob.getBinaryStream())
                    );
                    while ((line = bufferedReader.readLine()) != null) {
                        receiptBuffer.append(line);
                    }

                    receipt = receiptBuffer.toString();
                }

                Reimbursement reimbursement = new Reimbursement(
                        reimbId,
                        amount,
                        submitted,
                        resolved,
                        description,
                        receipt,
                        userDAO.getUserById(userId),
                        userDAO.getUserById(resolver),
//                        Status.values()[statusId - 1],
                        statusId,
//                        Type.values()[typeId - 1]
                        typeId
                );

                reimbursementList.add(reimbursement);
            }

            return reimbursementList;
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Reimbursement> getAllReimbs() {
        List<Reimbursement> reimbursementList = new ArrayList<>();

        try (Connection connection = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM ers_reimbursement ORDER BY ers_reimbursement.reimb_id";

            Statement statement = connection.createStatement();

            ResultSet resultset = statement.executeQuery(sql);

            while (resultset.next()) {
                int reimbId = resultset.getInt("reimb_id");
                double amount = resultset.getDouble("reimb_amount");
                Timestamp submitted = resultset.getTimestamp("reimb_submitted");
                Timestamp resolved = resultset.getTimestamp("reimb_resolved");
                String description = resultset.getString("reimb_description");
                int author = resultset.getInt("reimb_author");
                int resolver = resultset.getInt("reimb_resolver");
                int statusId = resultset.getInt("reimb_status_id");
                int typeId = resultset.getInt("reimb_type_id");

                Blob receiptBlob = resultset.getBlob("reimb_receipt");
                String receipt = null;
                if (receiptBlob != null) {
                    StringBuffer receiptBuffer = new StringBuffer();
                    String line;
                    BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(receiptBlob.getBinaryStream())
                    );
                    while ((line = bufferedReader.readLine()) != null) {
                        receiptBuffer.append(line);
                    }

                    receipt = receiptBuffer.toString();
                }

                Reimbursement reimbursement = new Reimbursement(
                        reimbId,
                        amount,
                        submitted,
                        resolved,
                        description,
                        receipt,
                        userDAO.getUserById(author),
                        userDAO.getUserById(resolver),
//                        Status.values()[statusId - 1],
                        statusId,
//                        Type.values()[typeId - 1]
                        typeId
                );

                reimbursementList.add(reimbursement);
            }

            return reimbursementList;
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean createReimb(Reimbursement reimbursement) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String sql = "INSERT INTO ers_reimbursement (" +
                        "reimb_amount, " +
                        "reimb_submitted, " +
                        "reimb_description, " +
                        "reimb_receipt, " +
                        "reimb_author, " +
                        "reimb_status_id, " +
                        "reimb_type_id) " +
                    "VALUES (?, CURRENT_TIMESTAMP, ?, ?, ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setDouble(1, reimbursement.getAmount());
            statement.setString(2, reimbursement.getDescription());
            Blob blob = connection.createBlob();
            if (reimbursement.getReceipt() != null) {
                blob.setBytes(1, reimbursement.getReceipt().getBytes());
                statement.setBlob(3, blob);
            } else statement.setNull(3, Types.BLOB);
            statement.setBlob(3, blob);
            statement.setInt(4, reimbursement.getAuthor().getUserId());
            statement.setInt(5, 1);
            statement.setInt(6, reimbursement.getTypeId());

            if (statement.executeUpdate() > 0) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean updateReimb(Reimbursement reimbursement) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String sql = "UPDATE ers_reimbursement " +
                    "SET reimb_status_id = ?, " +
                        "reimb_resolver = ?, " +
                        "reimb_resolved = CURRENT_TIMESTAMP " +
                    "WHERE reimb_id = ?";

            PreparedStatement statement = connection.prepareStatement(sql);

            // 2 is approved, 3 is denied
            statement.setInt(1, reimbursement.getStatusId());
            statement.setInt(2, reimbursement.getResolver().getUserId());
            statement.setInt(3, reimbursement.getReimbId());

            if (statement.executeUpdate() > 0) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
