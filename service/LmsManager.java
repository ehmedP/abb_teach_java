package service;

import seed.LibrarySeeder;

import java.time.LocalDate;

public class LmsManager {

    public void execute() {

        Library library = new Library();

        LibrarySeeder.seed(library);

    }

}
