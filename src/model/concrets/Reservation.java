package src.model.concrets;

import java.util.Objects;

public class Reservation {

    private static int nextId = 1;

    private final Integer id;
    private Member member;
    private Book book;
    private Integer reservationDay;
    private Integer priorityScore;

    public Reservation(Member member, Book book, Integer reservationDay) {
        this.id = nextId++;

        this.member = member;
        this.book = book;
        this.reservationDay = reservationDay;

        this.priorityScore = calculatePriorityScore(member);
    }

    public Integer getId() {
        return id;
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

    public Integer getReservationDay() {
        return reservationDay;
    }

    public void setReservationDay(Integer reservationDay) {
        this.reservationDay = reservationDay;
    }

    public Integer getPriorityScore() {
        return priorityScore;
    }

    public void setPriorityScore(Integer priorityScore) {
        this.priorityScore = priorityScore;
    }

    private Integer calculatePriorityScore(Member member) {
        return switch (member.getType()) {
            case PREMIUM -> 1;
            case REGULAR -> 2;
            case STUDENT -> 3;
        };
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
                "id=" + id +
                ", member=" + member +
                ", book=" + book +
                ", reservationDay=" + reservationDay +
                ", priorityScore=" + priorityScore +
                '}';
    }
}
