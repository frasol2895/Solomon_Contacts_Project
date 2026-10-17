/*
 * Name: Frank Solomon
 * Date: 10/10/2026
 * Assignment: SDC330L Week 1 Project
 * Purpose: Represents a business contact with a company and job title.
 */

// INHERITANCE: BusinessContact extends the Contact parent class.
public class BusinessContact extends Contact {
    private String company;
    private String jobTitle;

    public BusinessContact(String firstName, String lastName,
                           String phone, String email, Address address,
                           String company, String jobTitle) {
        super(firstName, lastName, phone, email, address);
        this.company = company;
        this.jobTitle = jobTitle;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    @Override
    public String getContactType() {
        return "Business";
    }

    @Override
    public String getContactInformation() {
        return super.getContactInformation()
                + "\nCompany: " + company
                + "\nJob Title: " + jobTitle;
    }
}