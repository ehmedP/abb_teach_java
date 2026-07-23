package enums;

public enum MembershipTypeEnum {
    REGULAR("regular", "Regular"),
    PREMIUM("premium", "Premium"),
    STUDENT("student", "Student");

    private final String value;
    private final String label;

    MembershipTypeEnum(String value, String label) {
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
