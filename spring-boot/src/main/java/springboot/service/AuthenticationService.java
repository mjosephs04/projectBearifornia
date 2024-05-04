package springboot.service;

import org.springframework.stereotype.Service;
import springboot.UserType;
import springboot.database.Setup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@Service
public class AuthenticationService {

    //returns null if the username and password arent valid, otherwise returns a userType
    public static UserType authenticate(String username, String password) {
        String query = "SELECT USERTYPE FROM USERS WHERE USERNAME = ? AND PASSWORD = ?";

        try (Connection conn = Setup.getDBConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String userTypeString = rs.getString("USERTYPE");
                    return UserType.valueOf(userTypeString.toUpperCase());

                }
            }

        } catch (SQLException e) {
            //ADD LOGGING
            System.out.println("Failed to authenticate user.");
        }

        return null; // Return null if authentication fails
    }
}
