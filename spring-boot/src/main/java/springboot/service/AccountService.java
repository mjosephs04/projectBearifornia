package springboot.service;

import org.springframework.stereotype.Service;
import springboot.DateParsing;
import springboot.UserType;
import springboot.database.Setup;
import java.sql.*;
import java.time.LocalDate;

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

    //returns "success" if it worked, otherwise a fail message
    public static String checkIn(String checkInDate, String checkOutDate, Integer roomNumber){
        return changeStatus(checkInDate, checkOutDate, roomNumber, true);
    }

    //returns "success" if it worked, otherwise a fail message
    public static String checkOut(String checkInDate, String checkOutDate, Integer roomNumber){
        return changeStatus(checkInDate, checkOutDate, roomNumber, false);
    }

    private static String changeStatus(String checkInDate, String checkOutDate, Integer roomNumber, Boolean status){
        LocalDate start = DateParsing.convertStringToDate(checkInDate);
        LocalDate end = DateParsing.convertStringToDate(checkOutDate);

        String selectQuery = "UPDATE RESERVATIONS SET STATUS = ? WHERE ROOMNUMBER = ? AND STARTDATE = ? AND ENDDATE = ?";

        try (Connection conn = Setup.getDBConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectQuery)) {

            pstmt.setBoolean(1, status);
            pstmt.setInt(2, roomNumber);
            pstmt.setDate(3, Date.valueOf(start));
            pstmt.setDate(4, Date.valueOf(end));

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected == 0) {
                return "failure -- No matching reservation found to modify or status was already desired value.";
            } else {
                return "success";
            }

        } catch (SQLException e) {
            return "Failed to get the reservation to checkIn: " + e.getMessage();
        }
    }
}
