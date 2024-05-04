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
    private final Setup setup;

    @Autowired
    public BillService(Setup setup) {
        this.setup = setup;
    }


    public String getAccountNameFromUser(){
        User JohnDoe = UserFunctions.findUser(LoggedIn.getUsername());
        return JohnDoe.getName();
    }




}

