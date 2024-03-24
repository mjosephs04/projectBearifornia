package org.example;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

//jan

public class room {
    private Double cost = 0.00;
    private Integer roomNumber, numOfBeds, qualityLevel = 0;
    private String typeOfRoom, bedType = "";
    private boolean smokingAllowed = false;

    //DEFAULT CONSTRUCTOR
    public room(){}

    //CONSTRUCTOR
    public room(Double c, Integer roomNum, Integer numBed, Integer quality, String roomType, boolean smoking) {
        this.cost = c;
        this.roomNumber = roomNum;
        this.numOfBeds = numBed;
        this.qualityLevel = quality;
        this.typeOfRoom = roomType;
        this.smokingAllowed = smoking;
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



    @Override
    public boolean equals(Object obj){
        if(obj instanceof room){
            return ((room) obj).getRoomNumber().equals(roomNumber) &&
                    ((room) obj).getNumOfBeds().equals(numOfBeds) &&
                    ((room) obj).getQualityLevel().equals(qualityLevel) &&
                    ((room) obj).getCost().equals(cost) &&
                    ((room) obj).getTypeOfRoom().equals(typeOfRoom);
        }
        else{
            return false;
        }
    }
}
