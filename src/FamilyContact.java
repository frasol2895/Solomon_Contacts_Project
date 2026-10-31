/*
 * Name: Frank Solomon
 * Date: 10/31/2026
 * Assignment: 4.2 Project - Week 4 Database Interactions
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