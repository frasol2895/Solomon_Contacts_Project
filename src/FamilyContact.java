/*
 * Name: Frank Solomon
 * Date: 10/10/2026
 * Assignment: SDC330L Week 1 Project
 * Purpose: Represents a family contact and their family relationship.
 */

// INHERITANCE: FamilyContact extends the Contact parent class.
public class FamilyContact extends Contact {
    private String relationship;

    public FamilyContact(String firstName, String lastName,
                         String phone, String email, Address address,
                         String relationship) {
        super(firstName, lastName, phone, email, address);
        this.relationship = relationship;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
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