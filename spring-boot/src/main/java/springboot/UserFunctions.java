package springboot;

import springboot.database.Setup;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UserFunctions {

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
                if(userType == UserType.ADMIN) {
                    userList.add(new Admin(name, username, password));
                }
                else if(userType == UserType.GUEST) {
                    userList.add(new Guest(name, username, password));
                }
                else if(userType == UserType.CLERK) {
                    userList.add(new Clerk(name, username, password));
                }
            }
        }
        catch(SQLException e) {
            System.out.println("failure " + e);
        }
        return userList;
    }

    //returns the guest info associated with a username, or null if there is no user associated w it
    public static User findUser(String username) {
        String find = "SELECT * FROM USERS WHERE username = ?";
        try (Connection conn = Setup.getDBConnection();
             PreparedStatement pstmt = conn.prepareStatement(find)) {
            pstmt.setString(1, username);

            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    UserType type = UserType.valueOf(resultSet.getString("userType"));
                    switch (type) {
                        case ADMIN:
                            return new Admin(
                                    resultSet.getString("name"),
                                    resultSet.getString("username"),
                                    resultSet.getString("password")
                            );
                        case GUEST:
                            return new Guest(
                                    resultSet.getString("name"),
                                    resultSet.getString("username"),
                                    resultSet.getString("password")
                            );
                        case CLERK:
                            return new Clerk(
                                    resultSet.getString("name"),
                                    resultSet.getString("username"),
                                    resultSet.getString("password")
                            );
                    }
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        //returns null if the SQL statement didnt work or if the
        //user was not found
        return null;
    }
}
