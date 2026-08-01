package src.model;

import java.util.Objects;

public class Book {

    private static Integer nextId = 1;

    private final Integer id;
    private String title;
    private String author;
    private Integer year;
    private Double rating;
    private Boolean isAvailable;

    public Book(String title, String author, Integer year, Double rating, Boolean isAvailable) {
        this.id = nextId++;

        this.title = title;
        this.author = author;
        this.year = year;
        this.rating = rating;
        this.isAvailable = isAvailable;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(getId(), book.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                ", rating=" + rating +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
