/*
 * Name: Frank Solomon
 * Date: 10/17/2026
 * Assignment: SDC330L Week 2 Project
 * Purpose: Provides a console menu for the Contacts application.
 * Creates sample contacts and demonstrates interfaces, polymorphism,
 * inheritance, and composition.
 */

import java.util.ArrayList;
import java.util.Scanner;

public class App {
    private static final ArrayList<Contact> contacts = new ArrayList<>();

    public static void main(String[] args) {
        createSampleContacts();

        try (Scanner input = new Scanner(System.in)) {
            System.out.println("========================================");
            System.out.println("Project Week 2 - Contacts Application");
            System.out.println("Software Design, Interfaces, and Polymorphism");
            System.out.println("Developer: Frank Solomon");
            System.out.println("========================================");
            System.out.println("Welcome to the Contacts application!");
            System.out.println("Enter a menu number and press Enter.");
            System.out.println("This demo includes three sample contacts.");
            System.out.println("Select 1 to display all contacts.");
            System.out.println("Select 2 to search by last-name initial.");
            System.out.println("Select 0 when you are ready to exit.");

            boolean running = true;

            // Repeat the menu until the user chooses to quit.
            while (running) {
                displayMenu();

                if (!input.hasNextLine()) {
                    System.out.println("\nInput closed. Goodbye!");
                    break;
                }

                String choice = input.nextLine().trim();

                switch (choice) {
                    case "1":
                        displayAllContacts();
                        break;

                    case "2":
                        searchByLastName(input);
                        break;

                    case "0":
                        System.out.println(
                                "Thank you for using the Contacts application. Goodbye!");
                        running = false;
                        break;

                    default:
                        System.out.println(
                                "Invalid selection. Please enter 1, 2, or 0.");
                }
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\n========== MAIN MENU ==========");
        System.out.println("1. Display all contacts");
        System.out.println("2. Search by last-name initial");
        System.out.println("0. Exit");
        System.out.print("Enter your selection: ");
    }

    private static void createSampleContacts() {
        // Fictional, realistic sample data for the Week 2 demonstration.

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

        // A collection of Contact references holds different subclass objects.
        contacts.add(business);
        contacts.add(family);
        contacts.add(friend);
    }

    private static void displayAllContacts() {
        System.out.println("\n========== ALL CONTACTS ==========");

        if (contacts.isEmpty()) {
            System.out.println("No contacts are available.");
            return;
        }

        // INTERFACE POLYMORPHISM: Displayable references handle all contact types.
        // Java calls the overridden method belonging to each actual object.
        for (Displayable contact : contacts) {
            System.out.println(contact.getContactInformation());
            System.out.println("----------------------------------");
        }

        System.out.println("Total contacts: " + contacts.size());
    }

    private static void searchByLastName(Scanner input) {
        System.out.print("Enter the first letter of the last name: ");

        if (!input.hasNextLine()) {
            return;
        }

        String letter = input.nextLine().trim();

        // Accept one letter and reject blank or invalid entries.
        if (letter.length() != 1
                || !Character.isLetter(letter.charAt(0))) {
            System.out.println("Please enter exactly one letter.");
            return;
        }

        boolean found = false;

        System.out.println("\n========== SEARCH RESULTS ==========");

        for (Contact contact : contacts) {
            // Ignore capitalization when comparing the initial.
            if (contact.getLastName().regionMatches(
                    true, 0, letter, 0, 1)) {

                // INHERITANCE POLYMORPHISM: A Contact reference calls
                // the matching subclass's overridden information method.
                System.out.println(contact.getContactInformation());
                System.out.println("----------------------------------");
                found = true;
            }
        }

        if (!found) {
            System.out.println(
                    "No contacts have a last name beginning with " + letter + ".");
        }
    }
}