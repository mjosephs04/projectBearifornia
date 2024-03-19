package org.example;

//jan

public class room {
    private Double cost = 0.00;
    private Integer roomNumber, numOfBeds, qualityLevel = 0;
    private String typeOfRoom = "";
    private boolean smokingAllowed = false;

    //CONSTRUCTOR
    public room(Double c, Integer roomNum, Integer numBed, Integer quality, String roomType, boolean smoking){
        this.cost = c;
        this.roomNumber = roomNum;
        this.numOfBeds = numBed;
        this.qualityLevel = quality;
        this.typeOfRoom = roomType;
        this.smokingAllowed = smoking;
    }

    //GETTERS
    public Double getCost(){
        return this.cost;
    }
    public Integer getRoomNumber(){
        return this.roomNumber;
    }
    public Integer getNumOfBeds(){
        return this.numOfBeds;
    }
    public Integer getQualityLevel(){
        return this.qualityLevel;
    }
    public String getTypeOfRoom(){
        return this.typeOfRoom;
    }
    public boolean getSmokingStatus(){
        return this.smokingAllowed;
    }

    //SETTERS
    public void setCost(Double c){
        this.cost = c;
    }
    public void setRoomNumber(Integer num){
        this.roomNumber = num;
    }
    public void setNumOfBeds(Integer num){
        this.numOfBeds = num;
    }
    public void setQualityLevel(Integer num){
        this.qualityLevel = num;
    }
    public void setTypeOfRoom(String s){
        this.typeOfRoom = s;
    }
    public void setSmokingStatus(boolean b){
        this.smokingAllowed = b;
    }
}
