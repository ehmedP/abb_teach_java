package src.service;

import src.enums.ItemTypeEnum;
import src.model.Book;
import src.model.Item;
import src.model.Magazine;

import java.util.Scanner;

public class ItemManager {

    private static final Scanner scanner = new Scanner(System.in);

    public Item createItemByType(ItemTypeEnum type) {

        return switch (type) {
            case BOOK -> this.createBookItem();
            case MAGAZINE -> this.createMagazineItem();
            case null -> {
                System.out.println("Sorry, we couldn't create the item. Please choose a valid item type.");
                yield null;
            }
        };

    }

    private Book createBookItem() {

        Integer id = readPositiveInteger("Enter the book ID: ");
        String title = readRequiredString("Enter the book title: ");
        String author = readRequiredString("Enter the author's name: ");
        Integer pages = readPositiveInteger("Enter the number of pages: ");

        return new Book(id, title, true, author, pages);
    }

    private Magazine createMagazineItem() {

        Integer id = readPositiveInteger("Enter the magazine ID: ");
        String title = readRequiredString("Enter the magazine title: ");
        Integer issueNumber = readPositiveInteger("Enter the issue number: ");

        return new Magazine(id, title, true, issueNumber);
    }

    private String readRequiredString(String message) {

        while (true) {
            System.out.print(message);
            String value = scanner.nextLine().trim();

            if (!value.isBlank()) {
                return value;
            }

            System.out.println("This field cannot be empty.");
        }
    }

    private Integer readPositiveInteger(String message) {

        while (true) {
            System.out.print(message);

            if (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid number.");
                scanner.nextLine();
                continue;
            }

            int value = scanner.nextInt();
            scanner.nextLine();

            if (value > 0) {
                return value;
            }

            System.out.println("The value must be greater than 0.");
        }
    }

}
