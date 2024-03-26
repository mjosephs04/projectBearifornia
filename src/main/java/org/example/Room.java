package org.example;

//jan

public class Room {
    private Double cost = 0.00;
    private Integer roomNumber, numOfBeds, qualityLevel = 0;
    private String typeOfRoom, bedType = "";
    private boolean smokingAllowed = false;

    //DEFAULT CONSTRUCTOR
    public Room() {
    }

    //CONSTRUCTOR
    public Room(Double c, Integer roomNum, Integer numBed, Integer quality, String roomType, boolean smoking) {
        this.cost = c;
        this.roomNumber = roomNum;
        this.numOfBeds = numBed;
        this.qualityLevel = quality;
        this.typeOfRoom = roomType;
        this.smokingAllowed = smoking;
    }


    //PRINT INFO
    public void printRoomInfo() {
        System.out.println(roomNumber);
        System.out.println("Room type: " + typeOfRoom);
        System.out.println("Bed type: " + bedType);
        System.out.println("# of beds: " + numOfBeds);
        System.out.println("Quality level: " + qualityLevel);
        if (!smokingAllowed) {
            System.out.println("Smoking not allowed.");
        } else {
            System.out.println("Smoking allowed.");
        }
    }

    //GETTERS
    public Double getCost() {
        return this.cost;
    }

    //SETTERS
    public void setCost(Double c) {
        this.cost = c;
    }

    public Integer getRoomNumber() {
        return this.roomNumber;
    }

    public void setRoomNumber(Integer num) {
        this.roomNumber = num;
    }

    public Integer getNumOfBeds() {
        return this.numOfBeds;
    }

    public void setNumOfBeds(Integer num) {
        this.numOfBeds = num;
    }

    public Integer getQualityLevel() {
        return this.qualityLevel;
    }

    public void setQualityLevel(Integer num) {
        this.qualityLevel = num;
    }

    public String getTypeOfRoom() {
        return this.typeOfRoom;
    }

    public void setTypeOfRoom(String s) {
        this.typeOfRoom = s;
    }

    public boolean getSmokingStatus() {
        return this.smokingAllowed;
    }

    public void setSmokingStatus(boolean b) {
        this.smokingAllowed = b;
    }

    public String getBedType() {
        return this.bedType;
    }

    public void setBedType(String s) {
        this.bedType = s;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Room) {
            return ((Room) obj).getRoomNumber().equals(roomNumber) && ((Room) obj).getNumOfBeds().equals(numOfBeds) && ((Room) obj).getQualityLevel().equals(qualityLevel) && ((Room) obj).getCost().equals(cost) && ((Room) obj).getTypeOfRoom().equals(typeOfRoom) && ((Room) obj).getBedType().equals(bedType);

        } else {
            return false;
        }
    }
}
