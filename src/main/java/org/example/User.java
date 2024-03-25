package org.example;

public interface User {
    String name = "default";
    Integer idNumber = -1;

    //reserves a room and modifies csv accordingly,
    // returns a String with a confirmation/error message
    public String reserveRoom();

    //returns the name of the user
    public String getName();

    //sets the idNumber variable
    public void setIdNumber(Integer id);


    //sets the name variable
    public void setName(String x);


    //returns id number
    public Integer getIdNumber();

}
