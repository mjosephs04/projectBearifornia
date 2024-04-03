package springboot.service;

import org.springframework.stereotype.Service;
import springboot.Reservation;
import springboot.Room;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {

    private final Reservation reservationHelper = new Reservation(); // Utilize Reservation class

//    public List<Room> searchAvailableRooms(boolean smoking, String bedType, int numOfBeds, String roomType, LocalDate startDate, LocalDate endDate) {
//        try {
//            // Leverage the searchRooms method from Reservation class
//            return reservationHelper.searchRooms(smoking, bedType, numOfBeds, roomType, startDate, endDate);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return List.of(); // Return an empty list in case of exceptions
//        }
//    }

    // Method to handle creating a new reservation
    public String createReservation(Reservation newReservation) {
        return reservationHelper.createReservation(newReservation);
    }
}
