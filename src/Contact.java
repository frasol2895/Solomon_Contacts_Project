/*
 * Name: Frank Solomon
 * Date: 10/24/2026
 * Assignment: SDC330L 3.2 Project - Abstraction, Constructors, and Access Specifiers
 * Description: Abstract parent with shared contact data and display behavior.
 */

public abstract class Contact implements Displayable {
    // PRIVATE: callers use controlled public methods instead of direct field access.
    private int id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;

    // COMPOSITION: A Contact has an Address object.
    private Address address;

    // PROTECTED CONSTRUCTOR: subclasses initialize shared private state with super(...).
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

    // ABSTRACTION: every concrete subtype must supply its own category.
    // Shared display code calls this method without knowing the concrete class.
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
    // PROTECTED: subclasses reuse validation; this is not part of the public API.
    protected static String requireText(String value, String field) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(field + " is required.");
        }
        return value.trim();
    }
}
