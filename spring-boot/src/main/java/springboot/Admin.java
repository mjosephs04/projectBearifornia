package springboot;

public class Admin implements User{
    private static UserType classification = UserType.ADMIN;
    private String name;
    private Integer idNumber;


    public Admin(String name, Integer id){
        this.name = name;
        this.idNumber = id;
    }
    @Override
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
    public Integer getIdNumber() {
        return idNumber;
    }

    @Override
    public void setIdNumber(Integer id) {
        idNumber = id;
    }
}
