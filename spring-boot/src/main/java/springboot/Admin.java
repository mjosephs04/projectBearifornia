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

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
