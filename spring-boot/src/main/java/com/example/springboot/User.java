package com.example.springboot;

public interface User {
    // Reserves a room and modifies csv accordingly,
    // Returns a String with a confirmation/error message
    String reserveRoom(Room x);

    void setType();

    String getName();

    void setName(String x);

    Integer getIdNumber();

    void setIdNumber(Integer id);
}
