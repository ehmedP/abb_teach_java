package src.enums;

public enum NotificationTypeEnum {
    DUE_SOON("due_soon", "Due Soon"),
    OVERDUE("overdue", "Overdue"),
    RESERVATION_READY("reservation_ready", "Reservation Ready"),
    BLACKLIST_WARNING("blacklist_warning", "Blacklist Warning");

    private final String value;
    private final String label;

    NotificationTypeEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }
}
