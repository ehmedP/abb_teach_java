package model.concrets;

import enums.CopyStatusEnum;

import java.util.Objects;

public class BookCopy {

    private static int nextId = 1;

    private final Integer id;
    private Integer branchId;

    private Book book;
    private CopyStatusEnum status;

    public BookCopy(Book book, Integer branchId, CopyStatusEnum status) {
        this.id = nextId++;

        this.book = book;
        this.branchId = branchId;
        this.status = status;
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

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public CopyStatusEnum getStatus() {
        return status;
    }

    public boolean isBorrowed() {
        return this.status == CopyStatusEnum.BORROWED;
    }

    public void setStatus(CopyStatusEnum status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BookCopy bookCopy = (BookCopy) o;
        return Objects.equals(getId(), bookCopy.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "BookCopy{" +
                "id=" + id +
                ", branchId=" + branchId +
                ", book=" + book +
                ", status=" + status +
                '}';
    }
}
