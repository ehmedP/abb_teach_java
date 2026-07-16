package src.enums;

public enum FuelTypeEnum {

    PETROL(1, "Petrol"),
    DIESEL(2, "Dizel"),
    LPG(3, "LPG");

    final Integer value;
    final String label;

    FuelTypeEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }

    public Integer getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

}
