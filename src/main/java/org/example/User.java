package org.example;

public interface User {
    String name = "default";
    Integer idNumber = -1;

    //reserves a room and modifies csv accordingly,
    // returns a String with a confirmation/error message
    public String reserveRoom();

    //returns the name of the user
    public String getName();

    //returns whether it is an admin, clerk, or guest
    public User getType();


    //returns id number
    public Integer getIdNumber();

}
