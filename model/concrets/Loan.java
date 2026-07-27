package model.concrets;

import java.util.Objects;

public class Loan {

    private static int nextId = 1;

    private final Integer id;

    private BookCopy bookCopy;
    private Member member;
    private Integer borrowDay;
    private Integer dueDay;
    private Boolean returned;

    public Loan(BookCopy bookCopy, Member member, Integer borrowDay) {
        this(bookCopy, member, borrowDay, member.calculateDueDay(borrowDay));
    }

    public Loan(BookCopy bookCopy, Member member, Integer borrowDay, Integer dueDay) {
        this.id = nextId++;

        this.bookCopy = bookCopy;
        this.member = member;
        this.borrowDay = borrowDay;
        this.dueDay = dueDay;

        this.returned = false;
    }

    public Integer getId() {
        return id;
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

    public boolean isNotReturned() {
        return !getReturned();
    }

    public void setReturned(Boolean returned) {
        this.returned = returned;
    }

    public void markAsReturned() {
        setReturned(true);
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
                ", bookCopy=" + bookCopy +
                ", member=" + member +
                ", borrowDay=" + borrowDay +
                ", dueDay=" + dueDay +
                ", returned=" + returned +
                '}';
    }
}
