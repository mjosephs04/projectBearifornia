package springboot.service;

import org.springframework.stereotype.Service;
import springboot.database.Setup;
import springboot.UserType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Service
public class AccountService {

    private final Setup setup;

    public AccountService(Setup setup) {
        this.setup = setup;
    }

    public String createClerk(String name, String username, String password) {
        Connection conn = setup.getDBConnection();
        return addUser(conn, name, username, password, UserType.CLERK.toString());
    }

    public String createGuest(String name, String username, String password) {
        Connection conn = setup.getDBConnection();
        return addUser(conn, name, username, password, UserType.GUEST.toString());
    }

    private String addUser(Connection conn, String name, String username, String password, String userType) {
        String insertSQL = "INSERT INTO USERS (NAME, USERNAME, PASSWORD, USERTYPE) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = conn.prepareStatement(insertSQL)) {
            statement.setString(1, name);
            statement.setString(2, username);
            statement.setString(3, password);
            statement.setString(4, userType);
            statement.executeUpdate();
            return "success";
        } catch (SQLException e) {
            System.out.println("Could not insert user into database: " + e.getMessage());
            return "Could not insert user into database: " + e.getMessage();
        }
    }
}
