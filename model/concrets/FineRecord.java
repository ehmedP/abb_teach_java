package model.concrets;

import java.util.Date;
import java.util.Objects;

public class FineRecord {

    private Member member;
    private Double amount;
    private String reason;
    private Date day;

    public FineRecord(Member member, Double amount, String reason, Date day) {
        this.member = member;
        this.amount = amount;
        this.reason = reason;
        this.day = day;
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

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        FineRecord that = (FineRecord) o;
        return Objects.equals(getMember(), that.getMember()) &&
                Objects.equals(getAmount(), that.getAmount()) &&
                Objects.equals(getReason(), that.getReason()) &&
                Objects.equals(getDay(), that.getDay());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMember(), getAmount(), getReason(), getDay());
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
