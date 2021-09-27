package model.entity;

public enum ActivityStatus {
    ACTIVE("active"), NOT_ACTIVE("not_active");

    private final String value;

    ActivityStatus (String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
