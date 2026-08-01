package src.model;

import java.time.LocalDate;
import java.util.Objects;

public class BorrowRecord {

    private static Integer nextId = 1;

    private final Integer id;
    private Book book;
    private LocalDate borrowedDate;
    private LocalDate returnedDate;

    public BorrowRecord(Book book, LocalDate borrowedDate, LocalDate returnedDate) {
        this.id = nextId++;

        this.book = book;
        this.borrowedDate = borrowedDate;
        this.returnedDate = returnedDate;
    }

    public Integer getId() {
        return id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDate getBorrowedDate() {
        return borrowedDate;
    }

    public void setBorrowedDate(LocalDate borrowedDate) {
        this.borrowedDate = borrowedDate;
    }

    public LocalDate getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(LocalDate returnedDate) {
        this.returnedDate = returnedDate;
    }

    public Boolean isReturned() {
        return this.returnedDate != null;
    }

    public Boolean isNotReturned() {
        return !this.isReturned();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BorrowRecord that = (BorrowRecord) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BorrowRecord{" +
                "id=" + id +
                ", book=" + book +
                ", borrowedDate=" + borrowedDate +
                ", returnedDate=" + returnedDate +
                '}';
    }
}
