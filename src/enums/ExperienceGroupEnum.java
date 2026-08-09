package src.enums;

public enum ExperienceGroupEnum {
    ZERO_TO_TWO("0-2 il"),
    THREE_TO_FIVE("3-5 il"),
    FIVE_PLUS("5+ il");

    private final String label;

    ExperienceGroupEnum(String label) {
        this.label = label;
    }

    public String label() {
        return label;
    }
}