package springboot.service;

import org.springframework.stereotype.Service;
import springboot.UserType;
import springboot.database.Setup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Service
public class AccountService {

    private final Setup setup;

    public AccountService(Setup setup) {
        this.setup = setup;
    }

    public String createClerk(String name, String username, String password, UserType userType) {
        if (!userType.equals(UserType.ADMIN)) {
            return "Failure: Only admins can create clerk accounts.";
        }
        return addUser(name, username, password, UserType.CLERK.toString());
    }

    public String createGuest(String name, String username, String password, UserType userType) {
        if (!userType.equals(UserType.CLERK) && !userType.equals(UserType.GUEST)) {
            return "Failure: Only clerks and guests can create guest accounts.";
        }
        return addUser(name, username, password, UserType.GUEST.toString());
    }

    private String addUser(String name, String username, String password, String userType) {
        Connection conn = Setup.getDBConnection();
        String insertSQL = "INSERT INTO USERS (NAME, USERNAME, PASSWORD, USERTYPE) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = conn.prepareStatement(insertSQL)) {
            statement.setString(1, name);
            statement.setString(2, username);
            statement.setString(3, password);
            statement.setString(4, userType);
            statement.executeUpdate();
            return "success";
        } catch (SQLException e) {
            return "Could not insert user into database: " + e.getMessage();
        }
    }

    public String changePassword(String username, String newPassword) {
        Connection conn = Setup.getDBConnection();
        String updateSQL = "UPDATE USERS SET PASSWORD = ? WHERE USERNAME = ?";

        try (PreparedStatement statement = conn.prepareStatement(updateSQL)) {
            statement.setString(1, newPassword);
            statement.setString(2, username);
            int updated = statement.executeUpdate();
            if (updated > 0) {
                return "Password updated successfully.";
            } else {
                return "User not found.";
            }
        } catch (SQLException e) {
            return "Error updating password: " + e.getMessage();
        }
    }
}
