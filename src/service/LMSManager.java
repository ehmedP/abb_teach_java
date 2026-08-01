package src.service;

import src.model.Book;
import src.model.User;
import src.seed.BookSeeder;
import src.seed.UserSeeder;

import java.util.List;

public class LMSManager {

    private static final String SEPARATOR = "========================================";

    public void execute() {

        List<User> users = UserSeeder.seed();
        List<Book> books = BookSeeder.seed();

        LibraryService libraryService = new LibraryService(users, books);

        libraryService.sortBooks();
        printSection("SORTED BOOKS", () ->
                libraryService.getBooks().forEach(System.out::println)
        );

        libraryService.analyzeLibrary();
        printSection("LIBRARY ANALYSIS", () -> {
            System.out.println("Average Rating: " + libraryService.getAverageRating());

            System.out.println("\nAvailable Books (after 2000):");
            libraryService.getAvailableBooksByGTYear(2000).forEach(System.out::println);

            System.out.println("\nMost Borrowed Book:");
            libraryService.getMostBorrowedBook()
                    .ifPresentOrElse(
                            System.out::println,
                            () -> System.out.println("No borrow records found.")
                    );

            System.out.println("\nCurrently Reading:");
            libraryService.getCurrentBorrowedBooksByUserName()
                    .forEach((userName, currentBooks) -> System.out.println(userName + " -> " + currentBooks));

            System.out.println("\nBooks Grouped by Author (after 1950):");
            libraryService.getGroupedBooksByAuthor(1950)
                    .forEach((author, authorBooks) -> System.out.println(author + " -> " + authorBooks));
        });

        printSection("UNIQUE AUTHORS READ", () ->
                System.out.println(libraryService.uniqueAuthorsRead())
        );

        User aydin = users.get(0);
        printSection("RECOMMENDATION FOR " + aydin.getName().toUpperCase(), () ->
                libraryService.findRecommendedBookForUser(aydin)
                        .ifPresentOrElse(
                                book -> System.out.println("Recommended: " + book),
                                () -> System.out.println("No recommendation available.")
                        )
        );

        printSection("TOP READER OF OCTOBER 2025", () ->
                libraryService.findTopReaderOfMonth(users, 10, 2025)
                        .ifPresentOrElse(
                                user -> System.out.println("Top Reader: " + user.getName()),
                                () -> System.out.println("No reader found.")
                        )
        );
    }

    private void printSection(String title, Runnable body) {
        System.out.println(SEPARATOR);
        System.out.println(title);
        System.out.println(SEPARATOR);
        body.run();
        System.out.println();
    }

}
