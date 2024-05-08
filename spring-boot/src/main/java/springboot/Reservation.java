package springboot;

import springboot.service.SearchRoomsService;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Reservation {

    Room room;
    private Integer idNumber;
    private String username;
    private LocalDate startDay;
    private LocalDate endDay;
    private Integer price;
    private String email;
    private String address;

    public Reservation(Room room, LocalDate start, LocalDate end) {
        this.room = new Room(room);
        this.startDay = start;
        this.endDay = end;
    }

    public Reservation(Integer roomNum, LocalDate start, LocalDate end, String username) {
        try {
            this.room = SearchRoomsService.findRoom(roomNum);
        }catch(Exception e) {
            System.out.println("could not make reservation-- roomNum is not associated with a room" + e);
        }
        this.startDay = start;
        this.endDay = end;
        this.username = username;
    }

    //returns -1 if the start and end dates of the reservation are not valid
    public Double calculateCost(){
        double cost = -1.0;
        Integer days = (int)ChronoUnit.DAYS.between(getStartDay(), getEndDay());

        if(days > 0){
            cost = room.getCost() * days;
        }

        return cost;
    }

    //returns true if the given reservation conflicts with the desired times
    public boolean conflictsWith(LocalDate start, LocalDate end){
        boolean result = true;

        if(startDay.isBefore(start)){
            if(endDay.isAfter(start) || endDay.equals(startDay)){
                result = false;
            }
        }
        else if(startDay.isAfter(start)){
            if(startDay.isBefore(end)){
                result = false;
            }
        }
        else if(startDay.equals(endDay)){
            result = false;
        }


        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Reservation) {
            return ((Reservation) obj).getStartDay().equals(startDay) &&
                    ((Reservation) obj).getEndDay().equals(endDay) &&
                    ((Reservation) obj).getRoom().equals(room);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(room, endDay, startDay);
    }

    public Integer getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(Integer idNumber) {
        this.idNumber = idNumber;
    }

    public LocalDate getStartDay() {
        return startDay;
    }

    public void setStartDay(LocalDate startDay) {
        this.startDay = startDay;
    }

    public LocalDate getEndDay() {
        return endDay;
    }

    public void setEndDay(LocalDate endDay) {
        this.endDay = endDay;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}


