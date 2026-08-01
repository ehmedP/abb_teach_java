package src.seed;

import src.model.Book;

import java.util.Arrays;
import java.util.List;

public class BookSeeder {

    public static List<Book> seed() {

        return Arrays.asList(
                new Book("1984", "George Orwell", 1949, 4.9, true),
                new Book("Animal Farm", "George Orwell", 1945, 4.8, false),
                new Book("Homage to Catalonia", "George Orwell", 1938, 4.3, true),

                new Book("Clean Code", "Robert Martin", 2008, 4.7, true),
                new Book("Clean Architecture", "Robert Martin", 2017, 4.6, false),
                new Book("The Clean Coder", "Robert Martin", 2011, 4.4, true),

                new Book("Effective Java", "Joshua Bloch", 2018, 4.9, true),
                new Book("Java Puzzlers", "Joshua Bloch", 2005, 4.2, true),

                new Book("The Pragmatic Programmer", "Andy Hunt", 1999, 4.6, true),
                new Book("Practices of an Agile Developer", "Andy Hunt", 2006, 4.0, false),

                new Book("Java Concurrency in Practice", "Brian Goetz", 2006, 4.5, false),

                new Book("Design Patterns", "Erich Gamma", 1994, 4.6, true),

                new Book("Refactoring", "Martin Fowler", 1999, 4.5, true),
                new Book("Patterns of Enterprise Application Architecture", "Martin Fowler", 2002, 4.3, false),
                new Book("Domain-Driven Design", "Eric Evans", 2003, 4.4, true),

                new Book("Introduction to Algorithms", "Thomas Cormen", 2009, 4.8, true),

                new Book("The Hobbit", "J.R.R. Tolkien", 1937, 4.9, true),
                new Book("The Fellowship of the Ring", "J.R.R. Tolkien", 1954, 4.9, false),
                new Book("The Two Towers", "J.R.R. Tolkien", 1954, 4.8, true),
                new Book("The Return of the King", "J.R.R. Tolkien", 1955, 4.9, false),

                new Book("Harry Potter and the Philosopher's Stone", "J.K. Rowling", 1997, 4.7, true),
                new Book("Harry Potter and the Chamber of Secrets", "J.K. Rowling", 1998, 4.6, true),
                new Book("Harry Potter and the Prisoner of Azkaban", "J.K. Rowling", 1999, 4.8, false),

                new Book("A Game of Thrones", "George R.R. Martin", 1996, 4.7, true),
                new Book("A Clash of Kings", "George R.R. Martin", 1998, 4.6, true),

                new Book("Sapiens", "Yuval Noah Harari", 2011, 4.7, true),
                new Book("Homo Deus", "Yuval Noah Harari", 2015, 4.5, false),
                new Book("21 Lessons for the 21st Century", "Yuval Noah Harari", 2018, 4.3, true),

                new Book("Brave New World", "Aldous Huxley", 1932, 4.6, true),
                new Book("To Kill a Mockingbird", "Harper Lee", 1960, 4.8, true),
                new Book("The Great Gatsby", "F. Scott Fitzgerald", 1925, 4.4, false),
                new Book("Crime and Punishment", "Fyodor Dostoevsky", 1866, 4.7, true)
        );
    }

}
