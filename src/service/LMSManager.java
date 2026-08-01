package src.service;

import src.model.Book;
import src.model.User;
import src.seed.BookSeeder;
import src.seed.UserSeeder;

import java.util.List;

public class LMSManager {

    public void execute() {

        List<User> users = UserSeeder.seed();
        List<Book> books = BookSeeder.seed();

        LibraryService libraryService = new LibraryService(users, books);

    }


}
