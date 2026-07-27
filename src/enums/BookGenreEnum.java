package src.enums;

public enum BookGenreEnum {

    FICTION("fiction", "Fiction"),
    SCIENCE("science", "Science"),
    HISTORY("history", "History"),
    TECHNOLOGY("technology", "Technology");

    private final String value;
    private final String label;

    BookGenreEnum(String value, String label) {
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