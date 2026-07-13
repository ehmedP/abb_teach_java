package src.enums;

public enum ItemTypeEnum {

    BOOK(1, "Book"),
    MAGAZINE(2, "Magazine");

    private final String label;
    private final Integer value;

    ItemTypeEnum(Integer value, String label) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public Integer getValue() {
        return value;
    }

    public static ItemTypeEnum fromCode(String code) {

        for (ItemTypeEnum item : values()) {
            if (item.name().equalsIgnoreCase(code.trim())) {
                return item;
            }
        }

        return null;
    }
}
