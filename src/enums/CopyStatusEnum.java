package src.enums;

public enum CopyStatusEnum {
    AVAILABLE("available", "Available"),
    BORROWED("borrowed", "Borrowed"),
    LOST("lost", "Lost"),
    IN_TRANSIT("in_transit", "In Transit");

    private final String value;
    private final String label;

    CopyStatusEnum(String value, String label) {
        this.label = label;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }
}
