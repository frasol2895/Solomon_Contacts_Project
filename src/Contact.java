/*
 * Name: Frank Solomon
 * Date: 10/31/2026
 * Assignment: SDC330L 4.6 Course Project - Database Implementation
 * Description: Abstract parent with shared contact data and display behavior.
 */

public abstract class Contact implements Displayable {
    private int id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;

    // COMPOSITION: A Contact has an Address object.
    private Address address;

    protected Contact(String firstName, String lastName,
                   String phone, String email, Address address) {
        this.firstName = requireText(firstName, "firstName");
        this.lastName = requireText(lastName, "lastName");
        this.phone = requireText(phone, "phone");
        this.email = requireText(email, "email");
        this.address = java.util.Objects.requireNonNull(address, "Address is required.");
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = requireText(firstName, "firstName");
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = requireText(lastName, "lastName");
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = requireText(phone, "phone");
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = requireText(email, "email");
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = java.util.Objects.requireNonNull(address, "Address is required.");
    }

    public abstract String getContactType();

    public int getId() {
        return id;
    }

    // Package access: only application classes assign an ID, and only once.
    void assignId(int id) {
        if (this.id != 0 || id <= 0) {
            throw new IllegalArgumentException("ID must be positive and assigned only once.");
        }
        this.id = id;
    }

    // Returns the information shared by every contact type.
    @Override
    public String getContactInformation() {
        return "ID: " + id + "\nType: " + getContactType()
                + "\nName: " + firstName + " " + lastName
                + "\nPhone: " + phone
                + "\nEmail: " + email
                + "\nAddress: " + address;
    }

    @Override
    public String toString() {
        return getContactInformation();
    }
    // Keep required data valid even when callers bypass the console.
    protected static String requireText(String value, String field) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(field + " is required.");
        }
        return value.trim();
    }
}
