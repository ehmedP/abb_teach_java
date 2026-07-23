package model.concrets;

import java.util.Date;
import java.util.Objects;

public class Loan {

    private Integer loanId;
    private BookCopy bookCopy;
    private Member member;
    private Date borrowDay;
    private Date dueDay;
    private Boolean returned;

    public Loan(Integer loanId, BookCopy bookCopy, Member member, Date borrowDay, Date dueDay, Boolean returned) {
        this.loanId = loanId;
        this.bookCopy = bookCopy;
        this.member = member;
        this.borrowDay = borrowDay;
        this.dueDay = dueDay;
        this.returned = returned;
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

    public Date getBorrowDay() {
        return borrowDay;
    }

    public void setBorrowDay(Date borrowDay) {
        this.borrowDay = borrowDay;
    }

    public Date getDueDay() {
        return dueDay;
    }

    public void setDueDay(Date dueDay) {
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
        return Objects.equals(getLoanId(), loan.getLoanId()) &&
                Objects.equals(getBookCopy(), loan.getBookCopy()) &&
                Objects.equals(getMember(), loan.getMember()) &&
                Objects.equals(getBorrowDay(), loan.getBorrowDay()) &&
                Objects.equals(getDueDay(), loan.getDueDay()) &&
                Objects.equals(getReturned(), loan.getReturned());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLoanId(), getBookCopy(), getMember(), getBorrowDay(), getDueDay(), getReturned());
    }

    @Override
    public String toString() {
        return "Loan{" +
                "loanId=" + loanId +
                ", bookCopy=" + bookCopy +
                ", member=" + member +
                ", borrowDay=" + borrowDay +
                ", dueDay=" + dueDay +
                ", returned=" + returned +
                '}';
    }
}
