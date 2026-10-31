/*
 * Name: Frank Solomon
 * Date: 10/31/2026
 * Assignment: SDC330L 4.6 Course Project - Database Implementation
 * Description: Stores and formats the address owned by a contact.
 */

public class Address {
    private String street;
    private String city;
    private String state;
    private String zipCode;

    // Constructor initializes the address information.
    public Address(String street, String city, String state,
                   String zipCode) {
        this.street = requireText(street, "street");
        this.city = requireText(city, "city");
        this.state = requireText(state, "state");
        this.zipCode = requireText(zipCode, "zipCode");
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = requireText(street, "street");
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = requireText(city, "city");
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = requireText(state, "state");
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = requireText(zipCode, "zipCode");
    }

    // Returns a readable mailing address.
    @Override
    public String toString() {
        return street + ", " + city + ", " + state + " " + zipCode;
    }
    // Keep required data valid even when callers bypass the console.
    private static String requireText(String value, String field) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(field + " is required.");
        }
        return value.trim();
    }
}
