package springboot;

import springboot.database.Setup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Admin implements User{
    private static UserType classification = UserType.ADMIN;
    private String name;
    private String username;
    private String password;


    public Admin(String name){
        this.name = name;
    }

    public Admin(String name, String username, String password){
        this.name = name;
        this.username = username;
        this.password = password;
    }
    @Override
    public UserType getType() {
        return classification;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String x) {
        name = x;
    }

    public String addUser(Connection conn, String user, String pass, String userT){
        String insertSQL = "INSERT INTO USERS (USERNAME, PASSWORD, USERTYPE) VALUES (?, ?, ?)";

        try (PreparedStatement statement = conn.prepareStatement(insertSQL)) {
            statement.setString(1, user);
            statement.setString(2, pass);
            statement.setString(3, userT);
            statement.executeUpdate();

            return "success";
        } catch(SQLException e) {
            System.out.println("Could not insert users into database: " + e.getMessage());
            return "Could not insert users into database: " + e.getMessage();
        }
    }

    //returns either "success" or a fail message
    public String addClerk(String username, String password, UserType type) {
        Connection conn = Setup.getDBConnection();

        if(type.equals(UserType.CLERK)){
            return addUser(conn, username, password, type.toString());
        }
        else{
            return "failure-- admins may only create CLERK accounts";
        }
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
