package model.concrets;

import enums.CopyStatusEnum;

import java.util.Objects;

public class BookCopy {

    private Integer id;
    private String copyId;
    private Book book;
    private String branchId;
    private CopyStatusEnum status;

    public BookCopy(Integer id, String copyId, Book book, String branchId, CopyStatusEnum status) {
        this.id = id;
        this.copyId = copyId;
        this.book = book;
        this.branchId = branchId;
        this.status = status;
    }

    public String getCopyId() {
        return copyId;
    }

    public void setCopyId(String copyId) {
        this.copyId = copyId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public CopyStatusEnum getStatus() {
        return status;
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
                "copyId=" + copyId +
                ", book=" + book +
                ", branchId=" + branchId +
                ", status=" + status +
                '}';
    }
}
