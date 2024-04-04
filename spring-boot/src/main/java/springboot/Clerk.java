package springboot;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Clerk implements User {

    private static UserType classification;
    private String name;
    private Integer idNumber;

    public Clerk() {
        classification = UserType.CLERK;
    }

    public Clerk(String n, Integer id) {
        this.name = n;
        this.idNumber = id;
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

    @Override
    public Integer getIdNumber() {
        return idNumber;
    }

    @Override
    public void setIdNumber(Integer id) {
        idNumber = id;
    }
}
