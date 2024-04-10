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
}
