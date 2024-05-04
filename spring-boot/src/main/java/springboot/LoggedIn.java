package springboot;


public class LoggedIn {
    public static String username = null;
    public static UserType type = null;

    public static void logIn(String u, UserType p){
        username = u;
        type = p;
    }

    //returns null if not logged in, otherwise returns username
    public static boolean isLoggedIn(){
        return username != null;
    }

    //returns null if not logged in, otherwise returns username
    public static String getUsername(){
        return username;
    }

    public static UserType getType(){
        return type;
    }
}
