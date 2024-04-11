package springboot;

public interface User {
    // Reserves a room and modifies csv accordingly,
    // Returns a String with a confirmation/error message
    UserType getType();

    String getName();

    void setName(String x);

    String getUsername();

}
