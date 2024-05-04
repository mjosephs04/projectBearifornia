package springboot.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot.*;
import springboot.database.Setup;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Service
public class BillService {


    public static String getAccountNameFromUser(){
        User JohnDoe = UserFunctions.findUser(LoggedIn.getUsername());
        return JohnDoe.getName();
    }


    public static Double getTax(Double Room, Double Shop){
        return 0.08 * (Room + Shop);
    }

    public static Double getPriceFinal(Double Room, Double Shop){
        return ((0.08 * (Room + Shop)) + (Room + Shop));
    }




}

