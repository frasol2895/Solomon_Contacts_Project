/*
 * Name: Frank Solomon
 * Date: 10/24/2026
 * Assignment: SDC330L 3.8 Course Project - Class Implementation
 * Description: Manages contacts and unique IDs in memory for Week 3.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ContactManager {
    private final List<Contact> contacts;
    private int nextId;

    public ContactManager() {
        contacts = new ArrayList<>();
        nextId = 1;
    }

    public void addContact(Contact contact) {
        Objects.requireNonNull(contact, "Contact is required.");
        contact.assignId(nextId);
        nextId++;
        contacts.add(contact);
    }

    public Contact findById(int id) {
        for (Contact contact : contacts) {
            if (contact.getId() == id) {
                return contact;
            }
        }
        return null;
    }

    public List<Contact> findByLastNameInitial(String initial) {
        if (initial == null || !initial.trim().matches("[A-Za-z]")) {
            throw new IllegalArgumentException("Please enter exactly one letter A-Z.");
        }
        List<Contact> matches = new ArrayList<>();
        for (Contact contact : contacts) {
            if (contact.getLastName().regionMatches(true, 0, initial.trim(), 0, 1)) {
                matches.add(contact);
            }
        }
        return Collections.unmodifiableList(matches);
    }

    // Replacement is validated before it reaches this method; preserve its ID.
    public boolean updateContact(int id, Contact replacement) {
        Objects.requireNonNull(replacement, "Contact is required.");
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getId() == id) {
                replacement.assignId(id);
                contacts.set(i, replacement);
                return true;
            }
        }
        return false;
    }

    public boolean removeContact(int id) {
        return contacts.removeIf(contact -> contact.getId() == id);
    }

    public List<Contact> getAllContacts() {
        // Protect list membership; callers use manager operations for CRUD.
        return Collections.unmodifiableList(new ArrayList<>(contacts));
    }
}
