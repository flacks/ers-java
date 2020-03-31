package com.revature.repo;

import com.revature.enums.user.Role;
import com.revature.models.User;
import com.revature.util.ConnectionUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    private static Logger logger = LogManager.getLogger(UserDAOImpl.class);

    @Override
    public User getUserById(int userId) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM ers_users WHERE ers_users_id = ?";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, userId);

            ResultSet resultset = statement.executeQuery();

            if (resultset.next()) {
                String username = resultset.getString("ers_username");
                String password = resultset.getString("ers_password");
                String firstName = resultset.getString("user_first_name");
                String lastName = resultset.getString("user_last_name");
                String email = resultset.getString("user_email");
                int roleId = resultset.getInt("user_role_id");

                User user = new User(
                        userId,
                        username,
                        password,
                        firstName,
                        lastName,
                        email,
                        Role.values()[roleId - 1]
                );

                logger.info("Fetched user by ID: " + user.getUserId());
                return user;
            }
        } catch (SQLException e) {
            logger.error("Error getting user by ID: " + e);
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM ers_users WHERE ers_username = ?";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, username);

            ResultSet resultset = statement.executeQuery();

            if (resultset.next()) {
                int userId = resultset.getInt("ers_users_id");
                String password = resultset.getString("ers_password");
                String firstName = resultset.getString("user_first_name");
                String lastName = resultset.getString("user_last_name");
                String email = resultset.getString("user_email");
                int roleId = resultset.getInt("user_role_id");

                User user = new User(
                        userId,
                        username,
                        password,
                        firstName,
                        lastName,
                        email,
                        Role.values()[roleId - 1]
                );

                logger.info("Fetched user by username: " + user.getUsername());
                return user;
            }
        } catch (SQLException e) {
            logger.error("Error getting user by username: " + e);
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        try (Connection connection = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM ers_users";

            Statement statement = connection.createStatement();

            ResultSet resultset = statement.executeQuery(sql);

            while (resultset.next()) {
                int userId = resultset.getInt("ers_users_id");
                String username = resultset.getString("ers_username");
                String password = resultset.getString("ers_password");
                String firstName = resultset.getString("user_first_name");
                String lastName = resultset.getString("user_last_name");
                String email = resultset.getString("user_email");
                int roleId = resultset.getInt("user_role_id");

                User user = new User(
                        userId,
                        username,
                        password,
                        firstName,
                        lastName,
                        email,
                        Role.values()[roleId - 1]
                );

                userList.add(user);
            }

            logger.info("Fetched list of all users");
            return userList;
        } catch (SQLException e) {
            logger.error("Error getting list of all users: " + e);
            e.printStackTrace();
        }

        return null;
    }
}
