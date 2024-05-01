package springboot.service;

import org.springframework.stereotype.Service;
import springboot.Reservation;
import springboot.Room;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {

    // Method to handle creating a new reservation
    public String createReservation(Reservation res) {
        return Reservation.addToDatabase(res.getStartDay(),
                                        res.getEndDay(),
                                        res.getRoom().getRoomNumber(),
                                        res.getUsername());
    }

    public String createReservation(String checkIn, String checkOut, int roomNumber, String name) {
        return Reservation.addToDatabase(checkIn, checkOut, roomNumber, name);
    }
}
