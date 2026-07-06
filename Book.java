public class Book {

    private String title;
    private String author;
    private String isbn;
    protected int totalCopies;
    protected int availableCopies;

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;

        this.totalCopies = 1;
        this.availableCopies = 1;
    }

    // Instance methods

    public void borrowBook() {

        if (this.availableCopies > 0) {
            System.out.println("Book borrowed successfully.");
            this.availableCopies--;
        } else {
            System.out.println("Sorry, no copies available.");
        }
    }

    public void returnBook() {

        if (availableCopies < totalCopies) {
            System.out.println("Book returned successfully.");
            availableCopies++;
        } else {
            System.out.println("All copies are already in the library.");
        }
    }

    public void printInfo() {

        System.out.println("------------------------------------------------------------");
        System.out.println("Book title: "+ this.title);
        System.out.println("Book Author: "+ this.author);
        System.out.println("Book Isbn: "+ this.isbn);
        System.out.println("Book Total Copies: "+ this.totalCopies);
        System.out.println("Book Available Copies: "+ this.availableCopies);
        System.out.println("------------------------------------------------------------");

    }

    // Static methods

    public static void libraryRules() {
        System.out.println("Max 3 books can be borrowed per person.");
    }

    // Final methods

    public final void bookType() {
        System.out.println("This is a regular book.");
    }

    // Initializer blocks

    {
        System.out.println("New book created!");
    }

}
