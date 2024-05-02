package springboot;

import springboot.database.Setup;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        //                "CREATE TABLE USERS (Id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, name VARCHAR(255), username VARCHAR(255) NOT NULL, password VARCHAR(255), userType VARCHAR(255) NOT NULL)",
        String find = "SELECT * FROM USERS WHERE username = " + username;
        Connection conn = Setup.getDBConnection();
        try {
            ResultSet resultSet = conn.createStatement().executeQuery(find);
            if (resultSet.next()) {//if the room was found, make a new room w its info
                UserType type = UserType.valueOf(resultSet.getString("userType"));
                if(type == UserType.ADMIN) {
                    return new Admin(resultSet.getString("name"),
                            resultSet.getString("username"),
                            resultSet.getString("password"));
                }
                else if(type == UserType.GUEST) {
                    return new Guest(resultSet.getString("name"),
                            resultSet.getString("username"),
                            resultSet.getString("password"));
                }
                else if(type == UserType.CLERK) {
                    return new Clerk(resultSet.getString("name"),
                            resultSet.getString("username"),
                            resultSet.getString("password"));
                }
            }
        }
        catch(SQLException e) {
            return null;
        }

        return null;
    }
}
