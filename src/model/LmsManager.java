package src.model;

import src.enums.LmsMenuItem;

import java.util.Scanner;

public class LmsManager {

    private static final Scanner globalScanner = new Scanner(System.in);
    private static final Library library = new Library();

    public void execute() {

        seedLibrary();

        while (true) {

            switch (takeMainMenuOption()) {

                case ADD:
                    handleAddProcess();
                    break;

                case SHOW:
                    handleShowProcess();
                    break;

                case CHECKOUT:
                    handleCheckoutProcess();
                    break;

                case RETURN:
                    handleReturnProcess();
                    break;

                case SEARCH:
                    handleSearchProcess();
                    break;

                case EXIT:
                    finish();
                    return;
            }
        }
    }

    private void handleAddProcess() {

    }

    private void handleShowProcess() {

    }

    private void handleCheckoutProcess() {

    }

    private void handleReturnProcess() {

    }

    private void handleSearchProcess() {

    }

    private LmsMenuItem takeMainMenuOption() {

        while (true) {

            System.out.println("-------------------------------------------------------------------------------------------");
            System.out.println("-------------------------------- Library Management System --------------------------------");

            for (LmsMenuItem item : LmsMenuItem.values()) {
                System.out.println(item.getValue() + ". " + item.getLabel() + " - " + item);
            }

            System.out.print("Enter command (e.g. ADD): ");

            String option = globalScanner.nextLine();

            LmsMenuItem menuItem = LmsMenuItem.fromCode(option);

            System.out.println(menuItem);

            if (menuItem != null) {
                return menuItem;
            }

            System.out.println("Invalid menu option. Please try again.");
        }
    }

    private void finish() {
        System.out.println("----------------------------------------- Finish ------------------------------------------");
        globalScanner.close();
    }

    private void seedLibrary() {

        library.addItem(new Book(1, "Clean Code", true, "Robert C. Martin", 464));
        library.addItem(new Book(2, "Effective Java", true, "Joshua Bloch", 416));
        library.addItem(new Book(3, "Design Patterns", true, "GoF", 395));
        library.addItem(new Book(4, "Refactoring", true, "Martin Fowler", 448));
        library.addItem(new Book(5, "Java Concurrency in Practice", true, "Brian Goetz", 432));
        library.addItem(new Book(6, "Head First Java", true, "Kathy Sierra", 720));
        library.addItem(new Book(7, "Spring in Action", true, "Craig Walls", 520));
        library.addItem(new Book(8, "Algorithms", true, "Robert Sedgewick", 976));
        library.addItem(new Book(9, "The Pragmatic Programmer", true, "Andrew Hunt", 352));
        library.addItem(new Book(10, "Domain-Driven Design", true, "Eric Evans", 560));

        library.addItem(new Magazine(101, "National Geographic", true, 202401));
        library.addItem(new Magazine(102, "Time", true, 202402));
        library.addItem(new Magazine(103, "Forbes", true, 202403));
        library.addItem(new Magazine(104, "Science", true, 202404));
        library.addItem(new Magazine(105, "Nature", true, 202405));
        library.addItem(new Magazine(106, "IEEE Spectrum", true, 202406));
        library.addItem(new Magazine(107, "PC Magazine", true, 202407));
        library.addItem(new Magazine(108, "Linux Journal", true, 202408));
        library.addItem(new Magazine(109, "Wired", true, 202409));
        library.addItem(new Magazine(110, "MIT Technology Review", true, 202410));
    }

}
