package model.concrets;

import java.util.Objects;

public class Loan {

    private static int nextId = 1;

    private final Integer id;

    private Integer loanId;
    private BookCopy bookCopy;
    private Member member;
    private Integer borrowDay;
    private Integer dueDay;
    private Boolean returned;

    public Loan(Integer loanId, BookCopy bookCopy, Member member, Integer borrowDay, Integer dueDay, Boolean returned) {
        this.id = nextId++;

        this.loanId = loanId;
        this.bookCopy = bookCopy;
        this.member = member;
        this.borrowDay = borrowDay;
        this.dueDay = dueDay;
        this.returned = returned;
    }

    public Integer getId() {
        return id;
    }

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public BookCopy getBookCopy() {
        return bookCopy;
    }

    public void setBookCopy(BookCopy bookCopy) {
        this.bookCopy = bookCopy;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Integer getBorrowDay() {
        return borrowDay;
    }

    public void setBorrowDay(Integer borrowDay) {
        this.borrowDay = borrowDay;
    }

    public Integer getDueDay() {
        return dueDay;
    }

    public void setDueDay(Integer dueDay) {
        this.dueDay = dueDay;
    }

    public Boolean getReturned() {
        return returned;
    }

    public void setReturned(Boolean returned) {
        this.returned = returned;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Loan loan = (Loan) o;
        return Objects.equals(getId(), loan.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Loan{" +
                "id=" + id +
                ", loanId=" + loanId +
                ", bookCopy=" + bookCopy +
                ", member=" + member +
                ", borrowDay=" + borrowDay +
                ", dueDay=" + dueDay +
                ", returned=" + returned +
                '}';
    }
}
