package src.enums;

public enum LmsMenuItem {

    ADD(1, "Add Item"),
    SHOW(2, "Show Paginated Items"),
    SEARCH(3, "Search Item"),
    CHECKOUT(4, "Check Out Item"),
    RETURN(5, "Return Item"),
    EXIT(6, "Exit");

    private final String label;
    private final Integer value;

    LmsMenuItem(Integer value, String label) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public Integer getValue() {
        return value;
    }

    public static LmsMenuItem fromCode(String code) {

        for (LmsMenuItem item : values()) {
            if (item.name().equalsIgnoreCase(code.trim())) {
                return item;
            }
        }

        return null;
    }
}