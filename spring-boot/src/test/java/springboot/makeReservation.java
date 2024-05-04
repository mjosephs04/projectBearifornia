package springboot;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import springboot.controller.ReservationController;
import springboot.service.ReservationService;

import java.time.LocalDate;

public class makeReservation {
    Reservation reservation;
    ReservationController reservationController;

    @BeforeEach
    public void setUp() {
        LoggedIn.logIn("catherine", UserType.GUEST);
        //101,437.46,Nature Retreat,1,4,Single,true
        reservation = new Reservation(101,
                LocalDate.now().plusDays(3),
                LocalDate.now().plusDays(7),
                "catherine");
        reservationController = new ReservationController();
        //checkIn, checkOut, roomNumber, USERNAME!!!!!!----> username is only passed if a clerk is making
        //                                              a reservation on behalf of a guest, and in that case
        //                                              then it should be the guest's username
        //if a guest is logged in, you DO NOT NEED TO PASS
        //                                              A FOURTH STRING
    }

    @Test
    public void createReservation() {

    }
}
