package springboot;

import java.util.Objects;

public class Room {
    private Double cost = 0.00;
    private Integer roomNumber, numOfBeds;
    private String qualityLevel, typeOfRoom, bedType = "";
    private boolean smokingAllowed = false;

    //DEFAULT CONSTRUCTOR
    public Room() {
    }

    //CONSTRUCTOR
    public Room(Integer roomNum, Double c, String roomType, Integer numBed, String quality, String bedType, boolean smoking) {
        this.cost = c;
        this.roomNumber = roomNum;
        this.numOfBeds = numBed;
        this.qualityLevel = quality;
        this.typeOfRoom = roomType;
        this.smokingAllowed = smoking;
        this.bedType = bedType;
    }

    public Room(Room room){
        this.cost = room.getCost();
        this.roomNumber = room.getRoomNumber();
        this.numOfBeds = room.getNumOfBeds();
        this.qualityLevel = room.getQualityLevel();
        this.typeOfRoom = room.getTypeOfRoom();
        this.smokingAllowed = room.getSmokingAllowed();
        this.bedType = room.getBedType();
    }


    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Integer getNumOfBeds() {
        return numOfBeds;
    }

    public void setNumOfBeds(Integer numOfBeds) {
        this.numOfBeds = numOfBeds;
    }

    public String getQualityLevel() {
        return qualityLevel;
    }

    public void setQualityLevel(String qualityLevel) {
        this.qualityLevel = qualityLevel;
    }

    public String getTypeOfRoom() {
        return typeOfRoom;
    }

    public void setTypeOfRoom(String typeOfRoom) {
        this.typeOfRoom = typeOfRoom;
    }

    public boolean getSmokingAllowed() {
        return smokingAllowed;
    }

    public void setSmokingAllowed(boolean smokingAllowed) {
        this.smokingAllowed = smokingAllowed;
    }

    public String getBedType(){
        return bedType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room room)) return false;
        return getSmokingAllowed() == room.getSmokingAllowed() &&
                getRoomNumber().equals(room.getRoomNumber()) &&
                getNumOfBeds().equals(room.getNumOfBeds()) &&
                getBedType().equalsIgnoreCase(room.getBedType())&&
                getTypeOfRoom().equalsIgnoreCase(room.getTypeOfRoom());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCost(), getRoomNumber(), getNumOfBeds(), getQualityLevel(), getTypeOfRoom(), getTypeOfRoom(), smokingAllowed);
    }
}
