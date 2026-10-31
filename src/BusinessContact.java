/*
 * Name: Frank Solomon
 * Date: 10/31/2026
 * Assignment: SDC330L 4.6 Course Project - Database Implementation
 * Description: A contact with a company and job title.
 */

// INHERITANCE: BusinessContact extends the Contact parent class.
public class BusinessContact extends Contact {
    private String company;
    private String jobTitle;

    public BusinessContact(String firstName, String lastName,
                           String phone, String email, Address address,
                           String company, String jobTitle) {
        super(firstName, lastName, phone, email, address);
        this.company = requireText(company, "company");
        this.jobTitle = requireText(jobTitle, "jobTitle");
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = requireText(company, "company");
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = requireText(jobTitle, "jobTitle");
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