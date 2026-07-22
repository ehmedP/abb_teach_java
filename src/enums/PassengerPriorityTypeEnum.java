package src.enums;

public enum PassengerPriorityTypeEnum {

    ELDERLY("ELDERLY", "Yaşlı"),
    PREGNANT("PREGNANT", "Hamilə"),
    DISABLED("DISABLED", "Əlil"),
    VETERAN("VETERAN", "Veteran"),
    NONE("NONE", "Yoxdur");

    private final String value;
    private final String label;

    PassengerPriorityTypeEnum(String value, String label) {
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