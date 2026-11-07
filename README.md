# ContactBook
**Author: Frank Solomon**

ContactBook is a Java console application that keeps business, family, and friend contacts in a local SQLite database. It replaces scattered contact notes with searchable, persistent records.

## Features
- Add business, family, and friend contacts with mailing addresses.
- List contacts alphabetically and search by last-name initial.
- Update a contact by ID; press Enter to preserve a current value.
- Delete by ID with an explicit confirmation.
- Retain records after closing and reopening the program.
- Cancel an unfinished operation with `/cancel`.

## Run
Install a Java Development Kit (JDK) 17 or newer with `java` and `javac` on PATH.
All required JDBC and logging JARs are included in `lib/`.

**Windows:** Extract the complete folder, then double-click `run.bat`.
**macOS/Linux:** Open a terminal in the project folder and run `sh run.sh`.

**VS Code:** Open this folder, install Java support if needed, open `src/App.java`, and run its main method. The included VS Code settings resolve the bundled libraries.

The application creates `contacts.db` in the project folder on first use. A fresh installation has no contacts. Database files and compiled classes are excluded from Git.

## Terminal interaction
| Selection | Action | Input and output |
|---|---|---|
| 1 | Display all | Shows IDs, types, names, addresses, and subtype details |
| 2 | Search | Accepts one letter A–Z; displays matching last names |
| 3 | Add | Requests a contact type, common fields, and subtype details |
| 4 | Update | Requests an ID, then prompts with current values |
| 5 | Delete | Requests an ID and YES confirmation |
| 0 | Exit | Closes the console; saved data remains |

Required text cannot be blank. IDs must be positive integers. Phone and email fields accept nonblank text; their formats are not validated. Search is limited to English A–Z initials. Contact type cannot be changed during an update.

## Design and implementation
| Requirement | Implementation |
|---|---|
| Abstract class | `Contact` defines common fields and abstract `getContactType()` |
| Inheritance | `BusinessContact`, `FamilyContact`, and `FriendContact` extend `Contact` |
| Composition | A `Contact` contains an `Address`; `ContactManager` receives a repository |
| Interface and polymorphism | `Displayable` provides a display contract; the menu invokes overridden subtype methods through that interface |
| Constructors | Constructors initialize required data and subclasses call the parent constructor |
| Access specifiers | Public operations, private fields, protected constructor/validation, and package-access ID assignment |
| SQLite CRUD | `ContactRepository` implements INSERT, SELECT, UPDATE, and DELETE through JDBC |
| Input/output | `App` reads terminal input and displays records and operation results |
| Persistent storage | JDBC reconstructs the correct contact subtype from each stored row |

## Files
- `src/`: nine Java source files.
- `lib/`: SQLite JDBC 3.46.1.0, SLF4J 2.0.13, and license notices.
- `run.bat` and `run.sh`: compile-and-run launchers.
- `VIDEO_LINK.md`: recording link location.
- `docs/Recording_Script.txt`: approximately five-minute presentation guide.
- `docs/Submission_Checklist.txt`: final upload and submission steps.
- `docs/Verification.txt`: verification results.

## Demonstration
See [video link](VIDEO_LINK.md). The recording must be added before final submission.

## Limitations and future improvements
This is a local, single-user console application. It has no login, cloud synchronization, encryption, or graphical interface. Future improvements could include full-name searching, email format checks, optional fields, and a graphical interface.

## Project Summary
ContactBook addresses the need to organize different kinds of contacts without losing information when the program closes. Users can create, view, search, edit, and delete records through a numbered terminal menu. SQLite provides persistent storage, while JDBC connects the Java application to the database.

The design separates terminal interaction, coordination, contact data, and storage. An abstract Contact class holds shared information. BusinessContact, FamilyContact, and FriendContact specialize that information through inheritance and overridden methods. Address demonstrates composition, and Displayable allows the menu to display different contact types through one contract. Constructors and access specifiers keep initialization and field access organized.

The implementation connects these classes to parameterized SQL operations and reconstructs the correct contact type when reading saved records. Required-field checks, cancellation, and delete confirmation help users avoid unintended changes. The result is a focused contact manager that demonstrates object-oriented design and persistent database operations in a usable console application.
