package springboot;


public class LoggedIn {
    public static String username = null;
    public static UserType type = null;

    public static void logIn(String u, UserType p){
        username = u;
        type = p;
    }

    //returns null if not logged in
    public static String isLoggedIn(){
        return username;
    }
}
