package com.example.springboot;

import java.io.Serializable;
import java.util.List;

public class Listing implements Serializable {

    private String name;
    private Double cost;
    private String description;

    Listing(){

    }

    Listing(String name, Double cost, String desc){
        this.name = name;
        this.cost = cost;
        this.description = desc;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}

