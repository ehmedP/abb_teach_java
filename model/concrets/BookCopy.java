package model.concrets;

import enums.CopyStatusEnum;

import java.util.Objects;

public class BookCopy {

    private Integer copyId;
    private Book book;
    private Integer branchId;
    private CopyStatusEnum status;

    public BookCopy(Integer copyId, Book book, Integer branchId, CopyStatusEnum status) {
        this.copyId = copyId;
        this.book = book;
        this.branchId = branchId;
        this.status = status;
    }

    public Integer getCopyId() {
        return copyId;
    }

    public void setCopyId(Integer copyId) {
        this.copyId = copyId;
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

    public void setStatus(CopyStatusEnum status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BookCopy bookCopy = (BookCopy) o;
        return Objects.equals(getCopyId(), bookCopy.getCopyId()) &&
                Objects.equals(getBook(), bookCopy.getBook()) &&
                Objects.equals(getBranchId(), bookCopy.getBranchId()) && getStatus() == bookCopy.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCopyId(), getBook(), getBranchId(), getStatus());
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
