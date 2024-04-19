package springboot;

public class Clerk implements User {

    private static UserType classification;
    private String name;
    private String username;
    private String password;

    public Clerk() {
        classification = UserType.CLERK;
    }

    public Clerk(String n) {
        this.name = n;
        classification = UserType.CLERK;
    }

    public Clerk(String name, String username, String password){
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public UserType getType() {
        return classification;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String x) {
        name = x;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
