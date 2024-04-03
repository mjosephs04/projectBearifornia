package springboot;

public class Admin implements User{
    @Override
    public UserType getType() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String x) {

    }

    @Override
    public Integer getIdNumber() {
        return null;
    }

    @Override
    public void setIdNumber(Integer id) {

    }
}
