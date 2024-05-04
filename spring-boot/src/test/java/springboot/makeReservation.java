package springboot;

import org.junit.Test;
import org.junit.jupiter.api.*;
import springboot.controller.ReservationController;
import springboot.database.InitializeDatabase;
import springboot.database.Setup;
import springboot.controller.AccountController;
import springboot.service.AccountService;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class makeReservation {
    ReservationController reservationController = new ReservationController();

    @BeforeAll
    public static void setUp() {
        Setup.initialize();
        InitializeDatabase.main(null);

        AccountController create = new AccountController(new AccountService(new Setup()));
        create.createGuest(new String[]{
                "cate", "catherine", "password", "GUEST"
        });

        LoggedIn.logIn("catherine", UserType.GUEST);
    }

    @Test
    public void createReservationWithZonedTime() {
        setUp();

        String message = reservationController.createReservation(new String[]{
                "2024-05-01T14:00:00.000Z",
                "2024-05-03T11:00:00.000Z",
                "101"
        }).getBody();

        assertEquals("success", message);
        //this works ^^

        Reservation result = reservationController.showMyReservations().getBody();
        String reservation = result.getRoom().getRoomNumber() + " " +
                            result.getStartDay() + " " + result.getEndDay();
        //assertEquals("101 ", reservation);
        //this works ^^

//----------------------
        //the functions im testing after this require an admin to execute
        //LoggedIn.logIn("admin", UserType.ADMIN);//-------------------------------

        //x.getAllGuests().getBody().forEach(guest -> System.out.println(guest.getUsername()));
        //this works ^^
/*
        System.out.println(x.updateReservation(new String[] {
                            "2024-05-03",
                            "2024-05-07",
                            "101",
                            "2024-05-01",
                            "2024-05-03"
        }).getBody());

        result = x.showMyReservations().getBody();
        System.out.println(result.getRoom().getRoomNumber() + " " +
                result.getStartDay() + " " + result.getEndDay());
        //this works^^^


        //payload contains: String checkInDate, String checkOutDate, int roomNumber
        System.out.println(x.deleteReservation(new String[]{
                "2024-05-03T14:00:00.000Z",
                "2024-05-07T11:00:00.000Z",
                "101"
        }).getBody());*/
    }
}
