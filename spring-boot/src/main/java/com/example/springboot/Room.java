package com.example.springboot;

//jan

public class Room {
    private Double cost = 0.00;
    private Integer roomNumber, numOfBeds, qualityLevel = 0;
    private String typeOfRoom, bedType = "";
    private boolean smokingAllowed = false;

    //DEFAULT CONSTRUCTOR
    public Room(){}

    //CONSTRUCTOR
    public Room(Integer roomNum, Double c, String roomType, Integer numBed,
                Integer quality, String bedType, boolean smoking) {
        this.cost = c;
        this.roomNumber = roomNum;
        this.numOfBeds = numBed;
        this.qualityLevel = quality;
        this.typeOfRoom = roomType;
        this.smokingAllowed = smoking;
        this.bedType = bedType;
    }

    //PRINT INFO
    public void printRoomInfo(){
        System.out.println(roomNumber);
        System.out.println("Room type: " + typeOfRoom);
        System.out.println("Bed type: " + bedType);
        System.out.println("# of beds: " + numOfBeds);
        System.out.println("Quality level: " + qualityLevel);
        if(!smokingAllowed){
            System.out.println("Smoking not allowed.");
        }
        else{
            System.out.println("Smoking allowed.");
        }
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
    public String getBedType(){return this.bedType;}

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
    public void setBedType(String s){this.bedType = s;}


    @Override
    public boolean equals(Object obj){
        if(obj instanceof Room){
            return ((Room) obj).getRoomNumber().equals(roomNumber) &&
                    ((Room) obj).getNumOfBeds().equals(numOfBeds) &&
                    ((Room) obj).getQualityLevel().equals(qualityLevel) &&
                    ((Room) obj).getCost().equals(cost) &&
                    ((Room) obj).getTypeOfRoom().equals(typeOfRoom) &&
                    ((Room) obj).getBedType().equals(bedType);

        }
        else{
            return false;
        }
    }
}
