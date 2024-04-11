package springboot;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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

    //returns either "success" or a fail message
    public String addUser(String username, String password, UserType type) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("spring-boot/src/main/resources/Users.csv"));
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to read in the currently available rooms";
        }

        // Add the new line containing the new room
        StringBuilder newUser = new StringBuilder();
        newUser.append(username).append(",").append(password).append(",").append(type.toString());
        if (!lines.contains(newUser)) {
            lines.add(newUser.toString());
        } else {
            return "User already exists";
        }

        FileWriter fw;
        try {
            fw = new FileWriter("spring-boot/src/main/resources/Users.csv");
        } catch (IOException x) {
            x.printStackTrace();
            return "Could not write to User database";
        }

        // Write the updated content back to the CSV file
        try (BufferedWriter writer = new BufferedWriter(fw)) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Could not write to User database";
        }

        return "success";
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
