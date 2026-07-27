package src.model.concrets;

import src.enums.NotificationTypeEnum;

import java.util.Objects;

public class Notification {

    private static int nextId = 1;

    private final Integer id;
    private NotificationTypeEnum type;
    private String message;
    private Integer day;

    public Notification(NotificationTypeEnum type, String message, Integer day) {
        this.id = nextId++;

        this.type = type;
        this.message = message;
        this.day = day;
    }

    public Integer getId() {
        return id;
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

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
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
                "id=" + id +
                ", type=" + type +
                ", message='" + message + '\'' +
                ", day=" + day +
                '}';
    }
}
