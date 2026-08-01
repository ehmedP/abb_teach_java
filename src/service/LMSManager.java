package src.service;

import src.model.Book;
import src.model.User;
import src.seed.BookSeeder;
import src.seed.UserSeeder;

import java.util.List;

public class LMSManager {

    private static final String SEPARATOR = "========================================";

    public void execute() {

        List<Book> books = BookSeeder.seed();
        List<User> users = UserSeeder.seed(books);

        LibraryService libraryService = new LibraryService(users, books);

        libraryService.sortBooks();
        printSection("SORTED BOOKS", () ->
                libraryService.getBooks().forEach(System.out::println)
        );

        printSection("LIBRARY ANALYSIS", libraryService::analyzeLibrary);

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
