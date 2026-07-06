void main() {

    Book b1 = new Book("Clean Code", "Robert C. Martin", "1234567890");

    b1.printInfo();
    b1.borrowBook();
    b1.returnBook();
    Book.libraryRules();
    b1.bookType();

}