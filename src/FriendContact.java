/*
 * Name: Frank Solomon
 * Date: 10/10/2026
 * Assignment: SDC330L Week 1 Project
 * Purpose: Represents a friend contact and records where we met.
 */

// INHERITANCE: FriendContact extends the Contact parent class.
public class FriendContact extends Contact {
    private String whereWeMet;

    public FriendContact(String firstName, String lastName,
                         String phone, String email, Address address,
                         String whereWeMet) {
        super(firstName, lastName, phone, email, address);
        this.whereWeMet = whereWeMet;
    }

    public String getWhereWeMet() {
        return whereWeMet;
    }

    public void setWhereWeMet(String whereWeMet) {
        this.whereWeMet = whereWeMet;
    }

    @Override
    public String getContactType() {
        return "Friend";
    }

    @Override
    public String getContactInformation() {
        return super.getContactInformation()
                + "\nWhere We Met: " + whereWeMet;
    }
}