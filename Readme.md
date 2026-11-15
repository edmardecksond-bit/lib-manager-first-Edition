# Library Manager

A Java console application for managing a library, built as the final project of the Object-Oriented Programming course.

## Purpose

The application manages a catalog of items (books, DVDs, magazines) and lets a member borrow and return items while enforcing business rules.

## How to run

Requirements: Java 17 and Maven.

```bash
mvn compile
java -cp target/classes com.nb.library.Main
```

Or from IntelliJ: open `Main.java` and click the Run button.

## How to run the tests

```bash
mvn test
```

## Main features

- Loads the catalog from a CSV file at startup
- Displays the catalog
- Borrows an item (maximum of 3 borrowed items per member)
- Returns an item
- Shows the member's current loans
- Handles errors with clear messages

## Main classes

| Class | Package | Role |
|---|---|---|
| `Item` | model | Abstract class: a library item (id, title, borrowed state) |
| `Book`, `DVD`, `Magazine` | model | Item types, each with its own specific attribute |
| `Member` | model | A library member and their list of loans |
| `Library` | service | Catalog and borrowing/returning rules |
| `ItemCsvLoader` | utils | Reads the data file |
| `ConsoleMenu` | ui | Handles the interaction with the user |
| `Main` | root | Wires the components together and starts the application |

## Technical decisions

1. **`Item` is an abstract class, not an interface.** It holds shared state (`id`, `title`, `borrowed`) and shared code (`borrow()`, `returnItem()`, `describe()`) that the subclasses reuse instead of duplicating. An interface cannot hold per-object state. It is abstract because a generic "item" does not exist, and to force each subclass to define its own loan duration.

2. **Custom exceptions instead of generic ones.** `ItemNotFoundException`, `BorrowLimitExceededException` and `DataLoadException` name each business error clearly. Their distinct types let the code catch each error separately, and the tests check exactly which rule was violated.

3. **`List.copyOf` in the getters.** `getItems()` and `getBorrowedItems()` return read-only copies. This prevents other classes from modifying the lists directly, for example to bypass the limit of 3 loans. Only `Library` and `Member` can change their own state.

4. **Validate before modifying in `returnItem`.** The item is removed from the member's loans first, and only then marked as returned. If the member never borrowed it, an exception stops the operation before the item's state is changed, so the data stays consistent.

##  inheritance 

`Book`, `DVD` and `Magazine` extend `Item`. A book, a DVD and a magazine are all items: they share the id, the title and the borrowing logic, and each adds its own attribute (author, duration, issue number).

## polymorphism 

`Library` stores every item in a `List<Item>`. `displayCatalog()` calls `describe()` on each element without any `instanceof` or cast: Java runs the version that matches the actual type of the object (book, DVD or magazine).

## the abstract class 

`Item` is abstract: a generic "item" is never created directly. It declares the abstract method `getLoanDurationDays()`, which each subclass must implement with its own loan duration.

## Custom exceptions

- `ItemNotFoundException`: no item matches the given id
- `BorrowLimitExceededException`: the member has reached the limit of 3 loans
- `DataLoadException`: data file not found or invalid line (includes the line number)

## Data  loading

At startup, `ItemCsvLoader` reads `src/main/resources/items.csv`. Each line follows the format `TYPE,id,title,value`, where the value is the author, the duration or the issue number depending on the type. The loader creates the matching objects. An invalid line raises a `DataLoadException` that reports the line number.

## SOLID

- **Single Responsibility**: each class has one job (`ConsoleMenu` talks to the user, `Library` applies the rules, `ItemCsvLoader` reads the file).
- **Open/Closed**: adding a new item type does not require modifying `Library`.
- **Liskov Substitution**: a `Book`, a `DVD` or a `Magazine` can replace an `Item` anywhere without breaking behavior.
- **Dependency Inversion**: `ConsoleMenu` receives its dependencies (`Library`, `Member`, `Scanner`) through its constructor instead of creating them itself.

## Tests

JUnit 5 tests in `src/test/java` cover normal cases (borrowing, returning), edge cases (the limit of 3 loans) and invalid cases (item not found, returning an item that was not borrowed).