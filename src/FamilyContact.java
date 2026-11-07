/*
 * Name: Frank Solomon
 * Date: 11/07/2026
 * Project: ContactBook - Final Contacts Application
 * Description: A contact with a family relationship.
 */

// INHERITANCE: FamilyContact extends the Contact parent class.
public class FamilyContact extends Contact {
    private String relationship;

    public FamilyContact(String firstName, String lastName,
                         String phone, String email, Address address,
                         String relationship) {
        super(firstName, lastName, phone, email, address);
        this.relationship = requireText(relationship, "relationship");
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = requireText(relationship, "relationship");
    }

    @Override
    public String getContactType() {
        return "Family";
    }

    @Override
    public String getContactInformation() {
        return super.getContactInformation()
                + "\nRelationship: " + relationship;
    }
}