/*
 * Name: Frank Solomon
 * Date: 11/07/2026
 * Project: ContactBook - Final Contacts Application
 * Description: A contact with a description of where we met.
 */

// INHERITANCE: FriendContact extends the Contact parent class.
public class FriendContact extends Contact {
    private String whereWeMet;

    public FriendContact(String firstName, String lastName,
                         String phone, String email, Address address,
                         String whereWeMet) {
        super(firstName, lastName, phone, email, address);
        this.whereWeMet = requireText(whereWeMet, "whereWeMet");
    }

    public String getWhereWeMet() {
        return whereWeMet;
    }

    public void setWhereWeMet(String whereWeMet) {
        this.whereWeMet = requireText(whereWeMet, "whereWeMet");
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