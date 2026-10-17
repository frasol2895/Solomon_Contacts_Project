/*
 * Name: Frank Solomon
 * Date: 10/10/2026
 * Assignment: SDC330L Week 1 Project
 * Purpose: Stores a contact's street, city, state, and ZIP code.
 * A Contact will contain an Address object to demonstrate composition.
 */

public class Address {
    private String street;
    private String city;
    private String state;
    private String zipCode;

    // Constructor initializes the address information.
    public Address(String street, String city, String state,
                   String zipCode) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    // Returns a readable mailing address.
    @Override
    public String toString() {
        return street + ", " + city + ", " + state + " " + zipCode;
    }
}