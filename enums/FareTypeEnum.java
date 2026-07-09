package enums;

public enum FareTypeEnum {

    ECONOMY("Economy", 1),
    STANDARD("Standard", 2),
    PREMIUM("Premium", 3),
    VIP("VIP", 4);

    private final String label;
    private final Integer value;

    FareTypeEnum(String label, Integer value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public Integer getValue() {
        return value;
    }

    public static FareTypeEnum fromValue(int value) {

        for (FareTypeEnum type : values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }

        // exceptionlar kecenden sonra illegal argument throw edile biler
        return null;
    }
}