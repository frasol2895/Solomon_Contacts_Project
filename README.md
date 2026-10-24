# Contacts Application — Week 3
Frank Solomon | SDC330L | October 24, 2026

Open this folder in VS Code with a JDK and the Java extensions installed.
Open src/App.java and click Run above main. Or, from this folder:

```
mkdir bin
javac -d bin src/*.java
java -cp bin App
```

Menu: 1 display, 2 search by last-name initial, 3 add, 4 update, 5 delete, 0 exit.
Type /cancel at any operation prompt to return to the menu. Update prompts
show current values; Enter keeps each value. All fields are required; phone,
email and ZIP are stored as text without strict format checks. Delete requires YES.
Three fictional samples load on startup. All changes are in memory and reset
on restart. SQLite and ContactRepository are scheduled for Week 4.

Displayable is the interface. Contact is abstract, with protected construction
and an abstract getContactType(). Three subclasses override display methods.
Contact owns Address (composition). ContactManager holds Contact objects;
App displays them via Displayable (polymorphism). Data fields are private.
The manager assigns positive session IDs which are never reused after deletion.

GitHub submission: upload the source, README, settings and report to the Java
project repository. Add the instructor using their verified GitHub username.
Git tags cannot contain spaces: use Phase-1 as the tag and Phase #1 as the
release title, subject to the instructor's tagging instructions. Submit the
release URL. Keep the ZIP and Word report for the schedule's file requirement.
