package org.example;

public class Guest {

    // Data Members
    private String name = "";
    private String streetAddress = "";
    private String cardNumber = "";
    private String dateOfExpiration = "";

    // Constructor and default constructor
    public Guest(){}
    public Guest(String name, String streetAddress, String cardNumber, String dateOfExpiration){
        this.name = name;
        this.streetAddress = streetAddress;
        this.cardNumber = cardNumber;
        this.dateOfExpiration = dateOfExpiration;
    }

    // GETTERS
    public String getName(){
        return this.name;
    }
    public String getStreetAddress(){
        return this.streetAddress;
    }

    public String getCardNumber(){
        return this.cardNumber;
    }

    public String getDateOfExpiration(){
        return this.dateOfExpiration;
    }


    // SETTERS
    public void setName(String name) {
        this.name = name;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setDateOfExpiration(String dateOfExpiration) {
        this.dateOfExpiration = dateOfExpiration;
    }
}
