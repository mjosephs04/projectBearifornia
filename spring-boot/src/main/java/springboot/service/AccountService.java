package springboot.service;

import org.springframework.stereotype.Service;
import springboot.*;
import springboot.database.Setup;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {

    public static String createClerk(String name, String username, String password, UserType userType) {
        if (!userType.equals(UserType.ADMIN)) {
            return "Failure: Only admins can create clerk accounts.";
        }
        return addUser(name, username, password, UserType.CLERK.toString());
    }

    public static String createGuest(String name, String username, String password, UserType userType) {
        if (!userType.equals(UserType.CLERK) && !userType.equals(UserType.GUEST)) {
            return "Failure: Only clerks and guests can create guest accounts.";
        }
        return addUser(name, username, password, UserType.GUEST.toString());
    }

    private static String addUser(String name, String username, String password, String userType) {
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

    public static String changePassword(String username, String newPassword) {
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

    public static List<User> readInAllUsers() {
        List<User> userList = new ArrayList<>();
        String selectSQL = "SELECT * FROM USERS";
        Connection conn = Setup.getDBConnection();

        try (PreparedStatement statement = conn.prepareStatement(selectSQL)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                // Retrieve values from the ResultSet and create User objects
                String name = resultSet.getString("name");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                UserType userType = UserType.valueOf(resultSet.getString("userType"));

                // Create a User object and add it to the list
                if (userType == UserType.ADMIN) {
                    userList.add(new Admin(name, username, password));
                } else if (userType == UserType.GUEST) {
                    userList.add(new Guest(name, username, password));
                } else if (userType == UserType.CLERK) {
                    userList.add(new Clerk(name, username, password));
                }
            }
        } catch (SQLException e) {
            System.out.println("failure " + e);
        }
        return userList;
    }

    //returns the guest info associated with a username, or null if there is no user associated w it
    public static User findUser(String username) {
        String find = "SELECT * FROM USERS WHERE username = ?";
        try (Connection conn = Setup.getDBConnection(); PreparedStatement pstmt = conn.prepareStatement(find)) {
            pstmt.setString(1, username);

            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    UserType type = UserType.valueOf(resultSet.getString("userType"));
                    switch (type) {
                        case ADMIN:
                            return new Admin(resultSet.getString("name"), resultSet.getString("username"), resultSet.getString("password"));
                        case GUEST:
                            return new Guest(resultSet.getString("name"), resultSet.getString("username"), resultSet.getString("password"));
                        case CLERK:
                            return new Clerk(resultSet.getString("name"), resultSet.getString("username"), resultSet.getString("password"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
