package model.concrets;

import java.time.LocalDate;
import java.util.Objects;

public class Reservation {

    private Integer id;
    private Member member;
    private Book book;
    private LocalDate reservationDay;
    private Integer priorityScore;

    public Reservation(Member member, Book book, LocalDate reservationDay, Integer priorityScore) {
        this.member = member;
        this.book = book;
        this.reservationDay = reservationDay;
        this.priorityScore = priorityScore;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDate getReservationDay() {
        return reservationDay;
    }

    public void setReservationDay(LocalDate reservationDay) {
        this.reservationDay = reservationDay;
    }

    public Integer getPriorityScore() {
        return priorityScore;
    }

    public void setPriorityScore(Integer priorityScore) {
        this.priorityScore = priorityScore;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "member=" + member +
                ", book=" + book +
                ", reservationDay=" + reservationDay +
                ", priorityScore=" + priorityScore +
                '}';
    }
}
