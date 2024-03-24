package org.example;

public class Guest {

    // Data Members
    private String name = "";
    private String streetAddress = "";


    // Constructor and default constructor
    public Guest(){}
    public Guest(String name, String streetAddress){
        this.name = name;
        this.streetAddress = streetAddress;
    }

    // GETTERS
    public String getName(){
        return this.name;
    }
    public String getStreetAddress(){
        return this.streetAddress;
    }


    // SETTERS
    public void setName(String name) {
        this.name = name;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

}
