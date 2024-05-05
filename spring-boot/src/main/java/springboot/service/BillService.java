package springboot.service;
import org.springframework.stereotype.Service;
import springboot.*;


@Service
public class BillService {


    public static String getAccountNameFromUser(){
        User JohnDoe = AccountService.findUser(LoggedIn.getUsername());
        return JohnDoe.getName();
    }


    public static Double getTax(Double Room, Double Shop){
        return 0.08 * (Room + Shop);
    }

    public static Double getPriceFinal(Double Room, Double Shop){
        return ((0.08 * (Room + Shop)) + (Room + Shop));
    }




}

