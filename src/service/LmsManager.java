package src.service;

import src.seed.LibrarySeeder;

public class LmsManager {

    public void execute() {

        Library library = new Library();

        LibrarySeeder.seed(library);

    }

}
