package src.model;

public class Book extends Item {

    private String author;
    private Integer pages;

    public Book(Integer id, String title, boolean isAvailable, String author, Integer pages) {

        super(id, title, isAvailable);

        this.author = author;
        this.pages = pages;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public void displayInfo() {

    }

}
