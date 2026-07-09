package enums;

public enum TransportTypeEnum {

    BUS("Avtobus", 1),
    TAXI("Taksi", 2),
    BICYCLE("Velosiped", 3),
    SCOOTER("Skuter", 4);

    private final String label;
    private final Integer value;

    TransportTypeEnum(String label, Integer value) {
        this.label = label;
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

    public static TransportTypeEnum fromValue(int value) {

        for (TransportTypeEnum type : values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }

        // exceptionlar kecenden sonra illegal argument throw edile biler
        return null;
    }

}
