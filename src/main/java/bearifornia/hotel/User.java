package bearifornia.hotel;

public interface User {
    String name = "default";
    Integer idNumber = -1;

    //reserves a room and modifies csv accordingly,
    // returns a String with a confirmation/error message
    String reserveRoom();

    //returns the name of the user
    String getName();

    //sets the name variable
    void setName(String x);

    //returns id number
    Integer getIdNumber();

    //sets the idNumber variable
    void setIdNumber(Integer id);

}
