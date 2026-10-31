/*
 * Name: Frank Solomon
 * Date: 10/31/2026
 * Assignment: 4.2 Project - Week 4 Database Interactions
 * Description: Coordinates contact operations using persistent storage instead of a cache.
 */

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ContactManager {
    private final ContactRepository repository;

    public ContactManager(ContactRepository repository) throws SQLException {
        this.repository = Objects.requireNonNull(repository, "Repository is required.");
        repository.initialize();
    }

    public void addContact(Contact contact) throws SQLException {
        repository.insert(Objects.requireNonNull(contact, "Contact is required."));
    }

    public Contact findById(int id) throws SQLException {
        return repository.findById(id);
    }

    public List<Contact> findByLastNameInitial(String initial) throws SQLException {
        if (initial == null || !initial.trim().matches("[A-Za-z]")) {
            throw new IllegalArgumentException("Please enter exactly one letter A-Z.");
        }
        return Collections.unmodifiableList(repository.findByInitial(initial.trim()));
    }

    public boolean updateContact(int id, Contact replacement) throws SQLException {
        return repository.update(id, Objects.requireNonNull(replacement, "Contact is required."));
    }

    public boolean removeContact(int id) throws SQLException {
        return repository.delete(id);
    }

    public List<Contact> getAllContacts() throws SQLException {
        return Collections.unmodifiableList(repository.loadAll());
    }
}
