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

    //returns the guest info associated with a username
    public static User findUser(String username) {
        List<User> guestList = readInAllUsers();

        guestList.removeIf(curr -> ! curr.getUsername().equals(username));
        //at the end of this loop, the list rooms should only contain the desired guest

        if(guestList.size() == 1){
            return guestList.get(0);
        }
        else{
            return null;
        }
    }
}
