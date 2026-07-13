# Library Management System (with Menu)

## Technology

- **Java version:** JDK 26

## Description

A library management system is required. The system must handle different item types such as
books (Book) and magazines (Magazine). All item types must derive from a common abstract class
(`Item`) and display their information differently through polymorphism (`displayInfo()`). The
library must support adding items, listing them, searching by title, checking out, and returning
items. User interaction is built through a text-based menu using a switch-case on `String`.

## Technical Requirements

1. Abstract class Item
   - Fields: title, id, isAvailable
   - Abstract method displayInfo()
   - Methods checkOut(), returnItem()
2. Subclasses Book (adds author, pages) and Magazine (adds issueNumber) — override
   displayInfo().
3. Class Library
   - Item[] items = new Item[10]; — plain array, manual index tracking (an int
   count field)
   - Methods: addItem(Item item), showAllItems(), checkOutItem(int id),
   returnItem(int id), searchByTitle(String keyword)
4. String methods — in searchByTitle(String keyword):
   - Use .toLowerCase(), .contains() or .equalsIgnoreCase(), and .trim() to
   make the search case-insensitive and whitespace-safe.
   - In addItem, use .length() to reject a blank/empty title (just skip with a printed
   message — no exceptions).
5. Switch-case on String — build a simple text menu in Main using a loop and switch on a
   String command (not int):

```java
Enter command: ADD, SHOW, SEARCH, CHECKOUT, RETURN, EXIT
String command = scanner.nextLine().trim().toUpperCase();
switch (command) {
    case "ADD":
        // create Book or Magazine, call library.addItem()
        break;
    case "SHOW":
        library.showAllItems();
        break;
    case "SEARCH":
        // ask for keyword, call searchByTitle
        break;
    case "CHECKOUT":
        // ask for id, call checkOutItem
        break;
    case "RETURN":
        // ask for id, call returnItem
        break;
    case "EXIT":
        System.out.println("Goodbye!");
        break;
    default:
        System.out.println("Invalid command, try again.");
}
```

## Console Output

![Console Output](images/img.png)
