package bearifornia.hotel;

public interface User {
    String name = "default";
    Integer idNumber = -1;

    // Reserves a room and modifies csv accordingly,
    // Returns a String with a confirmation/error message
    String reserveRoom(Room x);

    String getName();

    void setName(String x);

    Integer getIdNumber();

    void setIdNumber(Integer id);
}
