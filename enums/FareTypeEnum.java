package enums;

public enum FareTypeEnum {

    ECONOMY("Economy", 1, 10.0),
    STANDARD("Standard", 2, 18.0),
    PREMIUM("Premium", 3, 23.0),
    VIP("VIP", 4, 45.0);

    private final String label;
    private final Integer value;
    private final double extraFee;

    FareTypeEnum(String label, Integer value, double extraFee) {
        this.label = label;
        this.value = value;
        this.extraFee = extraFee;
    }

    public String getLabel() {
        return label;
    }

    public Integer getValue() {
        return value;
    }

    public double getExtraFee() {
        return extraFee;
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