package model.concrets;

import enums.NotificationTypeEnum;

import java.util.Date;
import java.util.Objects;

public class Notification {

    private NotificationTypeEnum type;
    private String message;
    private Date day;

    public Notification(NotificationTypeEnum type, String message, Date day) {
        this.type = type;
        this.message = message;
        this.day = day;
    }

    public NotificationTypeEnum getType() {
        return type;
    }

    public void setType(NotificationTypeEnum type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
        Notification that = (Notification) o;
        return getType() == that.getType() && Objects.equals(getMessage(), that.getMessage()) && Objects.equals(getDay(), that.getDay());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getType(), getMessage(), getDay());
    }

    @Override
    public String toString() {
        return "Notification{" +
                "type=" + type +
                ", message='" + message + '\'' +
                ", day=" + day +
                '}';
    }
}
