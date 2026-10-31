# Frank Solomon — Week 4 Contacts Application

SDC330L 4.6 Course Project — Database Implementation | October 31, 2026

## Run on Windows
1. Extract the entire ZIP. Open the extracted project folder.
2. Use JDK 17 or newer (a JRE alone cannot compile Java).
3. Double-click run.bat, or open this folder in VS Code and run src/App.java.
   The included VS Code settings add every lib JAR to the classpath.
4. A new contacts.db is created in this project folder. It starts empty.
5. Choose 3 to add a contact, 1 to display, 2 to search, 4 to update,
   5 to delete, and 0 to exit. Reopen to verify saved contacts remain.

Manual PowerShell commands from the project folder:

```powershell
New-Item -ItemType Directory -Force bin
javac -cp "lib/*" -d bin src/*.java
java -cp "bin;lib/*" App
```

On macOS/Linux with a JDK: `sh run.sh`.

## Data entry
Types: 1 Business, 2 Family, 3 Friend. Fields must contain text. Phone,
email, and ZIP are strings; strict format validation is not enforced.
Search uses one A-Z last-name initial. During an operation, /cancel cancels.
Update keeps a field when Enter is pressed; ID and type remain unchanged.
Delete requires YES. IDs come from SQLite and are not reused after deletion.
The database stores data locally without encryption. Do not upload actual
personal contacts to GitHub; contacts.db is excluded by .gitignore.

## Structure
src contains 9 Java files. Contact implements Displayable and owns an Address.
BusinessContact, FamilyContact, and FriendContact extend Contact. App collects
input and displays polymorphically. ContactManager delegates to ContactRepository.
Repository operations use JDBC prepared statements and try-with-resources.
Every read queries SQLite, so no separate in-memory collection can become stale.
New databases contain no samples. The one-table schema matches the Week 3 plan.
See the included Word document for the final proposal, design, schema, and tests.

## Dependencies
Included for offline execution once a JDK is installed:
- Xerial sqlite-jdbc 3.46.1.0 (Apache License 2.0)
- SLF4J API and NOP 2.0.13 (MIT License)
Dependency notices/licenses are in lib and within the JARs.
https://github.com/xerial/sqlite-jdbc
https://www.slf4j.org/license.html

## Validation
Compiled for Java 17 using Eclipse ECJ; executed with OpenJDK 17 on Linux.
Passed: all three subtypes through add, read, update, delete; restart persistence;
case-insensitive initial search; stable IDs; leading-zero ZIP; apostrophes;
invalid input; cancelled edits and deletion; EOF; empty restart after deletion;
new ID after deletions; database-triggered write failure and menu recovery.
Windows launcher is provided but was not executed on Windows in this environment.

## GitHub submission
Upload the extracted project contents to the same repository used for Phase-1,
replacing the earlier project files. Include src, lib, README, launchers, settings,
and the Word report; exclude bin and your runtime contacts.db.
Commit message: Implement SQLite CRUD for Week 4 Contacts application
Then create a release from that new commit:
- Tag: Phase-2
- Title: Phase #2 – Database Implementation
- Description: Added SQLite persistence and CRUD for Business, Family, and Friend
  contacts. Preserved OOP design, input validation, and updated proposal/design.
Publish the release and copy its actual URL into the assignment Website URL field.
The URL should end in /releases/tag/Phase-2. The ZIP and Word document also satisfy
those listed deliverables if the course provides file upload fields.

The handout uses Phase-# with the iteration number substituted. Git tags cannot
contain spaces, so use Phase-2 as the tag and Phase #2 in the release title.
https://git-scm.com/docs/git-check-ref-format
No repository upload, commit, release, or LMS submission has been performed.
