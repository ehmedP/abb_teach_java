package model.concrets;

import java.time.LocalDate;
import java.util.Objects;

public class FineRecord {

    private Integer id;
    private Member member;
    private Double amount;
    private String reason;
    private LocalDate day;

    public FineRecord(Member member, Double amount, String reason, LocalDate day) {
        this.member = member;
        this.amount = amount;
        this.reason = reason;
        this.day = day;
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        FineRecord that = (FineRecord) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "FineRecord{" +
                "member=" + member +
                ", amount=" + amount +
                ", reason='" + reason + '\'' +
                ", day=" + day +
                '}';
    }
}
