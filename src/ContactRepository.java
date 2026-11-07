/*
 * Name: Frank Solomon
 * Date: 11/07/2026
 * Project: ContactBook - Final Contacts Application
 * Description: Creates the SQLite table and performs parameterized CRUD with JDBC.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ContactRepository {
    private final String databaseUrl;
    private static final String COLUMNS = "contact_type, first_name, last_name, phone, email, "
            + "street, city, state, zip_code, company, job_title, relationship, where_we_met";

    public ContactRepository(String databaseUrl) {
        this.databaseUrl = Objects.requireNonNull(databaseUrl, "Database URL is required.");
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(databaseUrl);
    }

    public void initialize() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS contacts ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "contact_type TEXT NOT NULL CHECK(contact_type IN ('Business','Family','Friend')), "
                + "first_name TEXT NOT NULL, last_name TEXT NOT NULL, "
                + "phone TEXT NOT NULL, email TEXT NOT NULL, street TEXT NOT NULL, "
                + "city TEXT NOT NULL, state TEXT NOT NULL, zip_code TEXT NOT NULL, "
                + "company TEXT, job_title TEXT, relationship TEXT, where_we_met TEXT)";
        try (Connection connection = connect(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        }
    }

    public void insert(Contact contact) throws SQLException {
        if (contact.getId() != 0) {
            throw new IllegalArgumentException("Only a new contact can be inserted.");
        }
        String sql = "INSERT INTO contacts (" + COLUMNS + ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        // Obtain the ID on the same connection, before committing the transaction.
        try (Connection connection = connect()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                bind(statement, contact);
                statement.executeUpdate();
                int id;
                try (Statement keyStatement = connection.createStatement();
                        ResultSet keys = keyStatement.executeQuery("SELECT last_insert_rowid()")) {
                    if (!keys.next()) {
                        throw new SQLException("No contact ID was generated.");
                    }
                    long generatedId = keys.getLong(1);
                    if (generatedId <= 0 || generatedId > Integer.MAX_VALUE) {
                        throw new SQLException("Contact ID exceeds the supported range.");
                    }
                    id = (int) generatedId;
                }
                connection.commit();
                contact.assignId(id);
            } catch (SQLException ex) {
                try { connection.rollback(); } catch (SQLException rollback) { ex.addSuppressed(rollback); }
                throw ex;
            }
        }
    }

    public List<Contact> loadAll() throws SQLException {
        return query("SELECT * FROM contacts ORDER BY last_name COLLATE NOCASE, first_name COLLATE NOCASE, id", null);
    }

    public List<Contact> findByInitial(String initial) throws SQLException {
        return query("SELECT * FROM contacts WHERE last_name LIKE ? ORDER BY last_name COLLATE NOCASE, id", initial + "%");
    }

    public Contact findById(int id) throws SQLException {
        List<Contact> contacts = query("SELECT * FROM contacts WHERE id = ?", id);
        return contacts.isEmpty() ? null : contacts.get(0);
    }

    private List<Contact> query(String sql, Object parameter) throws SQLException {
        List<Contact> contacts = new ArrayList<>();
        try (Connection connection = connect(); PreparedStatement statement = connection.prepareStatement(sql)) {
            if (parameter != null) {
                statement.setObject(1, parameter);
            }
            try (ResultSet rows = statement.executeQuery()) {
                while (rows.next()) {
                    contacts.add(readContact(rows));
                }
            }
        }
        return contacts;
    }

    public boolean update(int id, Contact replacement) throws SQLException {
        if (replacement.getId() != 0) {
            throw new IllegalArgumentException("Update requires a new replacement object.");
        }
        String sql = "UPDATE contacts SET contact_type=?, first_name=?, last_name=?, phone=?, email=?, "
                + "street=?, city=?, state=?, zip_code=?, company=?, job_title=?, relationship=?, where_we_met=? "
                + "WHERE id=? AND contact_type=?";
        try (Connection connection = connect(); PreparedStatement statement = connection.prepareStatement(sql)) {
            bind(statement, replacement);
            statement.setInt(14, id);
            statement.setString(15, replacement.getContactType());
            boolean updated = statement.executeUpdate() == 1;
            if (updated) {
                replacement.assignId(id);
            }
            return updated;
        }
    }

    public boolean delete(int id) throws SQLException {
        try (Connection connection = connect();
                PreparedStatement statement = connection.prepareStatement("DELETE FROM contacts WHERE id=?")) {
            statement.setInt(1, id);
            return statement.executeUpdate() == 1;
        }
    }

    private void bind(PreparedStatement statement, Contact contact) throws SQLException {
        Address address = contact.getAddress();
        statement.setString(1, contact.getContactType());
        statement.setString(2, contact.getFirstName());
        statement.setString(3, contact.getLastName());
        statement.setString(4, contact.getPhone());
        statement.setString(5, contact.getEmail());
        statement.setString(6, address.getStreet());
        statement.setString(7, address.getCity());
        statement.setString(8, address.getState());
        statement.setString(9, address.getZipCode());
        statement.setString(10, contact instanceof BusinessContact ? ((BusinessContact) contact).getCompany() : null);
        statement.setString(11, contact instanceof BusinessContact ? ((BusinessContact) contact).getJobTitle() : null);
        statement.setString(12, contact instanceof FamilyContact ? ((FamilyContact) contact).getRelationship() : null);
        statement.setString(13, contact instanceof FriendContact ? ((FriendContact) contact).getWhereWeMet() : null);
    }

    private Contact readContact(ResultSet row) throws SQLException {
        try {
            Address address = new Address(row.getString("street"), row.getString("city"),
                    row.getString("state"), row.getString("zip_code"));
            String first = row.getString("first_name");
            String last = row.getString("last_name");
            String phone = row.getString("phone");
            String email = row.getString("email");
            Contact contact;
            switch (row.getString("contact_type")) {
                case "Business":
                    contact = new BusinessContact(first, last, phone, email, address,
                            row.getString("company"), row.getString("job_title"));
                    break;
                case "Family":
                    contact = new FamilyContact(first, last, phone, email, address, row.getString("relationship"));
                    break;
                case "Friend":
                    contact = new FriendContact(first, last, phone, email, address, row.getString("where_we_met"));
                    break;
                default:
                    throw new SQLException("Unknown stored contact type.");
            }
            long id = row.getLong("id");
            if (id <= 0 || id > Integer.MAX_VALUE) {
                throw new SQLException("Stored contact ID is outside the supported range.");
            }
            contact.assignId((int) id);
            return contact;
        } catch (IllegalArgumentException ex) {
            throw new SQLException("Stored contact contains invalid data: " + ex.getMessage(), ex);
        }
    }
}
