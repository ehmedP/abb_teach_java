package enums;

public enum TMSMenuItemEnum {

    TRAVEL(1, "Travel"),
    EXIT(2, "Exit");

    private final int value;
    private final String label;

    TMSMenuItemEnum(int value, String label) {
        this.value = value;
        this.label = label;
    }

    public int getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

    public static TMSMenuItemEnum fromValue(int value) {
        for (TMSMenuItemEnum item : values()) {
            if (item.value == value) {
                return item;
            }
        }

        return null;
    }

}