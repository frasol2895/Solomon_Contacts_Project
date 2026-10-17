/*
 * Name: Frank Solomon
 * Date: 10/17/2026
 * Assignment: SDC330L Week 1 Project
 * Purpose: Stores shared contact information and serves as the
 * parent class for business, family, and friend contacts.
 */

// INTERFACE IMPLEMENTATION: Contact provides the Displayable method.
public class Contact implements Displayable {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;

    // COMPOSITION: A Contact has an Address object.
    private Address address;

    public Contact(String firstName, String lastName,
                   String phone, String email, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getContactType() {
        return "General";
    }

    // Implements Displayable; subclasses override this with their own details.
    @Override
public String getContactInformation() {
        return "Type: " + getContactType()
                + "\nName: " + firstName + " " + lastName
                + "\nPhone: " + phone
                + "\nEmail: " + email
                + "\nAddress: " + address;
    }

    @Override
    public String toString() {
        return getContactInformation();
    }
}