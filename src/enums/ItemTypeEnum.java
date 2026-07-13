package src.enums;

public enum ItemTypeEnum {

    BOOK("book", "Book"),
    MAGAZINE("magazine", "Magazine");

    private final String label;
    private final String value;

    ItemTypeEnum(String value, String label) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }
}
