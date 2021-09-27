package model.entity;

public enum PaymentStatus {
    READY("ready"), SENT("sent");

    private final String value;

    PaymentStatus (String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
