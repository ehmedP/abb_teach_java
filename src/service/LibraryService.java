package src.service;

import org.w3c.dom.ls.LSOutput;
import src.model.Book;
import src.model.BorrowRecord;
import src.model.User;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class LibraryService {

    private List<Book> books;
    private List<User> users;

    public LibraryService(List<User> users, List<Book> books) {
        this.books = books;
        this.users = users;
    }

    public Optional<Book> findRecommendedBookForUser(User user) {

        if (user.getBorrowHistory().isEmpty()) {
            return Optional.empty();
        }

        Optional<Book> foundBook = Optional.empty();

        Optional<Book> mostBorrowedBook = user.getBorrowHistory().stream()
                .collect(Collectors.groupingBy(BorrowRecord::getBook, Collectors.counting()))
                .entrySet().stream()
                .max(Comparator.comparingLong(Map.Entry::getValue))
                .map(Map.Entry::getKey);

        if (mostBorrowedBook.isPresent()) {
            foundBook = this.getBooksByAuthor(mostBorrowedBook.get().getAuthor())
                    .stream()
                    .filter(book ->
                            !user.getBorrowHistory().stream()
                                    .map(BorrowRecord::getBook)
                                    .toList()
                                    .contains(book)
                    )
                    .findFirst();
        }

        return foundBook;
    }

    public List<Book> getBooksByAuthor(String author) {

        return this.getBooks().stream()
                .filter(book -> book.getAuthor().equals(author))
                .sorted(Comparator.comparing(Book::getRating, Comparator.reverseOrder()))
                .toList();
    }

    public void sortBooks() {

        Comparator<Book> bookComparator = Comparator
                .comparing(Book::getRating, Comparator.reverseOrder())
                .thenComparing(Book::getYear)
                .thenComparing(Book::getTitle);

        this.setBooks(
                this.getBooks().stream()
                        .sorted(bookComparator)
                        .toList()
        );
    }

    public void analyzeLibrary() {

        Double averageBookRating = this.getAverageRating();
        List<Book> availableBooks = this.getAvailableBooksByGTYear(2000);
        Optional<Book> mostBorrowedBook = this.getMostBorrowedBook();
        Map<String, List<Book>> currentBorrowedBooksByUserName = this.getCurrentBorrowedBooksByUserName();
        Map<String, List<Book>> groupingBooksByAuthor = this.getGroupedBooksByAuthor(1950);

    }

    public void uniqueAuthorsRead() {

    }

    public Double getAverageRating() {

        return this.getBooks().stream()
                .mapToDouble(Book::getRating)
                .average()
                .orElse(0.0);
    }

    public List<Book> getAvailableBooksByGTYear(Integer year) {

        return this.getBooks().stream()
                .filter(book -> book.getYear() > year && book.isAvailable())
                .toList();
    }

    public Optional<Book> getMostBorrowedBook() {

        return this.getUsers().stream()
                .flatMap(user -> user.getBorrowHistory().stream())
                .collect(Collectors.groupingBy(BorrowRecord::getBook, Collectors.counting()))
                .entrySet().stream()
                .max(Comparator.comparingLong(Map.Entry::getValue))
                .map(Map.Entry::getKey);
    }

    public Map<String, List<Book>> getCurrentBorrowedBooksByUserName() {

        return this.getUsers().stream()
                .collect(
                        Collectors.toMap(
                                User::getName,
                                user -> user.getBorrowHistory().stream()
                                        .filter(BorrowRecord::isNotReturned)
                                        .map(BorrowRecord::getBook)
                                        .toList()
                        )
                );
    }

    public Map<String, List<Book>> getGroupedBooksByAuthor(Integer year) {

        return this.getBooks().stream()
                .collect(
                        Collectors.groupingBy(
                                Book::getAuthor,
                                Collectors.filtering(book -> book.getYear() > year, Collectors.toList())
                        )
                );
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Book> getBooks() {
        return books;
    }

    public List<User> getUsers() {
        return users;
    }
}
