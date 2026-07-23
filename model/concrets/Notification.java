package model.concrets;

import enums.NotificationTypeEnum;

import java.time.LocalDate;
import java.util.Objects;

public class Notification {

    private Integer id;
    private NotificationTypeEnum type;
    private String message;
    private LocalDate day;

    public Notification(NotificationTypeEnum type, String message, LocalDate day) {
        this.type = type;
        this.message = message;
        this.day = day;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Notification that = (Notification) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
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
