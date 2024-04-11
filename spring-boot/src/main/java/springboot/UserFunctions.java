package springboot;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserFunctions {

    public static List<User> readInAllUsers() throws IOException {
        ArrayList<User> users = new ArrayList<>(); //store all the rooms we read in
        BufferedReader reader = new BufferedReader(new FileReader("spring-boot/src/main/resources/Users.csv"));

        reader.readLine(); //skip first line of header info
        String line;

        //name,username,password,userType
        //read in available rooms from csv and store in list
        while ((line = reader.readLine()) != null) {
            String[] split = line.split(",");
            if(split[3].equalsIgnoreCase("admin")){
                Admin curr = new Admin(split[0], split[1], split[2]);
                users.add(curr);
            }
            else if(split[3].equalsIgnoreCase("guest")){
                Guest curr = new Guest(split[0], split[1], split[2]);
                users.add(curr);
            }
            else{
                Clerk curr = new Clerk(split[0], split[1], split[2]);
                users.add(curr);
            }

        }

        return users;
    }

    //returns the guest info associated with a username
    public static User findUser(String username) throws IOException {
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
