/*
 * Name: Frank Solomon
 * Date: 10/24/2026
 * Assignment: SDC330L 3.8 Course Project - Class Implementation
 * Description: Runs the terminal menu and validates contact management input.
 */

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class App {
    private static final ContactManager manager = new ContactManager();

    // A dedicated signal lets /cancel abandon an unfinished edit safely.
    private static class Cancelled extends RuntimeException {
        private static final long serialVersionUID = 1L;
    }

    public static void main(String[] args) {
        createSampleContacts();
        System.out.println("Frank Solomon - Week 3 Contacts Application");
        System.out.println("Class Implementation - Abstraction and Contact Management");
        System.out.println("Three fictional samples are loaded. Changes last only this session.");
        System.out.println("Enter a menu number. Type /cancel during an operation to cancel.");
        try (Scanner input = new Scanner(System.in)) {
            boolean running = true;
            while (running) {
                displayMenu();
                try {
                    switch (read(input, "Selection: ")) {
                        case "1":
                            showContacts(manager.getAllContacts());
                            break;
                        case "2":
                            showContacts(manager.findByLastNameInitial(
                                    read(input, "Last-name initial (A-Z): ")));
                            break;
                        case "3":
                            manager.addContact(readContact(input, null));
                            System.out.println("Contact added.");
                            break;
                        case "4":
                            updateContact(input);
                            break;
                        case "5":
                            deleteContact(input);
                            break;
                        case "0":
                            running = false;
                            break;
                        default:
                            System.out.println("Invalid selection. Enter 0 through 5.");
                    }
                } catch (Cancelled ex) {
                    System.out.println("Operation cancelled. No changes saved.");
                } catch (IllegalArgumentException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (NoSuchElementException ex) {
            System.out.println("Input closed.");
        }
        System.out.println("Thank you for using the Contacts application. Goodbye!");
    }

    private static void displayMenu() {
        System.out.println("\n========== MAIN MENU ==========");
        System.out.println("1. Display all contacts");
        System.out.println("2. Search by last-name initial");
        System.out.println("3. Add contact");
        System.out.println("4. Update contact");
        System.out.println("5. Delete contact");
        System.out.println("0. Exit");
    }

    private static String read(Scanner input, String prompt) {
        System.out.print(prompt);
        String value = input.nextLine().trim();
        if (value.equalsIgnoreCase("/cancel")) {
            throw new Cancelled();
        }
        return value;
    }

    private static String field(Scanner input, String label, String current) {
        while (true) {
            String value = read(input, label + (current == null ? "" : " [" + current + "]") + ": ");
            if (value.isEmpty() && current != null) {
                return current;
            }
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("A value is required.");
        }
    }

    private static int readId(Scanner input) {
        try {
            int id = Integer.parseInt(read(input, "Contact ID: "));
            if (id > 0) {
                return id;
            }
        } catch (NumberFormatException ex) {
            // Use the same clear message for letters and out-of-range integers.
        }
        throw new IllegalArgumentException("Enter a positive whole-number contact ID.");
    }

    private static void showContacts(List<Contact> contacts) {
        if (contacts.isEmpty()) {
            System.out.println("No contacts found.");
        }
        for (Displayable contact : contacts) {
            // POLYMORPHISM: the runtime subtype supplies its extra details.
            System.out.println(contact.getContactInformation());
            System.out.println("----------------------------------");
        }
        System.out.println("Total contacts: " + contacts.size());
    }

    private static Contact readContact(Scanner input, Contact current) {
        String type;
        if (current == null) {
            do {
                type = read(input, "Type (1 Business, 2 Family, 3 Friend): ");
                if (!type.matches("[123]")) {
                    System.out.println("Choose 1, 2, or 3.");
                }
            } while (!type.matches("[123]"));
        } else {
            type = current instanceof BusinessContact ? "1"
                    : current instanceof FamilyContact ? "2" : "3";
            System.out.println("Press Enter to keep each current value. Contact type stays the same.");
        }
        String first = field(input, "First name", current == null ? null : current.getFirstName());
        String last = field(input, "Last name", current == null ? null : current.getLastName());
        String phone = field(input, "Phone", current == null ? null : current.getPhone());
        String email = field(input, "Email", current == null ? null : current.getEmail());
        Address oldAddress = current == null ? null : current.getAddress();
        String street = field(input, "Street", oldAddress == null ? null : oldAddress.getStreet());
        String city = field(input, "City", oldAddress == null ? null : oldAddress.getCity());
        String state = field(input, "State", oldAddress == null ? null : oldAddress.getState());
        String zip = field(input, "ZIP code", oldAddress == null ? null : oldAddress.getZipCode());
        Address address = new Address(street, city, state, zip);
        // Build a separate object; cancellation never modifies an existing contact.
        switch (type) {
            case "1":
                BusinessContact business = (BusinessContact) current;
                return new BusinessContact(first, last, phone, email, address,
                        field(input, "Company", business == null ? null : business.getCompany()),
                        field(input, "Job title", business == null ? null : business.getJobTitle()));
            case "2":
                FamilyContact family = (FamilyContact) current;
                return new FamilyContact(first, last, phone, email, address,
                        field(input, "Relationship", family == null ? null : family.getRelationship()));
            default:
                FriendContact friend = (FriendContact) current;
                return new FriendContact(first, last, phone, email, address,
                        field(input, "Where we met", friend == null ? null : friend.getWhereWeMet()));
        }
    }

    private static void updateContact(Scanner input) {
        int id = readId(input);
        Contact current = manager.findById(id);
        if (current == null) {
            System.out.println("Contact ID not found.");
            return;
        }
        System.out.println(current.getContactInformation());
        Contact replacement = readContact(input, current);
        manager.updateContact(id, replacement);
        System.out.println("Contact updated.");
    }

    private static void deleteContact(Scanner input) {
        int id = readId(input);
        Contact current = manager.findById(id);
        if (current == null) {
            System.out.println("Contact ID not found.");
            return;
        }
        System.out.println(current.getContactInformation());
        if (read(input, "Delete this contact? Enter YES to confirm: ").equalsIgnoreCase("YES")) {
            manager.removeContact(id);
            System.out.println("Contact deleted.");
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    private static void createSampleContacts() {
        // Fictional sample data for the Week 3 demonstration.

        // COMPOSITION: Each contact contains its own Address object.
        Address businessAddress =
                new Address("125 Market Street", "Richmond", "VA", "23220");

        Address familyAddress =
                new Address("240 Maple Lane", "Chesterfield", "VA", "23832");

        Address friendAddress =
                new Address("85 Oak Drive", "Midlothian", "VA", "23113");

        // INHERITANCE: These three classes extend Contact.
        BusinessContact business = new BusinessContact(
                "Jordan", "Carter",
                "804-555-0101", "jordan.carter@example.com",
                businessAddress, "River City Technology", "Project Manager");

        FamilyContact family = new FamilyContact(
                "Morgan", "Davis",
                "804-555-0102", "morgan.davis@example.com",
                familyAddress, "Cousin");

        FriendContact friend = new FriendContact(
                "Taylor", "Bennett",
                "804-555-0103", "taylor.bennett@example.com",
                friendAddress, "College");

        manager.addContact(business);
        manager.addContact(family);
        manager.addContact(friend);
    }

}
