package model.concrets;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class Loan {

    private Integer id;
    private String loanId;
    private BookCopy bookCopy;
    private Member member;
    private Date borrowDay;
    private LocalDate dueDay;
    private Boolean returned;

    public Loan(Integer id, String loanId, BookCopy bookCopy, Member member, Date borrowDay, LocalDate dueDay, Boolean returned) {
        this.id = id;
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

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
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

    public LocalDate getDueDay() {
        return dueDay;
    }

    public void setDueDay(LocalDate dueDay) {
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
                "loanId=" + loanId +
                ", bookCopy=" + bookCopy +
                ", member=" + member +
                ", borrowDay=" + borrowDay +
                ", dueDay=" + dueDay +
                ", returned=" + returned +
                '}';
    }
}
