package model.entity;

import model.builder.CreditCardBuilder;

import java.util.Objects;

public class CreditCard extends Model {
    private String name;
    private String pincode; //hashed value of 4-digit number
    private long balance;
    private String activityStatus;

    private CreditCard(CreditCardBuilderImpl builder) {
        super(builder.id);
        this.name = builder.name;
        this.pincode = builder.pincode;
        this.balance = builder.balance;
        this.activityStatus = builder.activityStatus;
    }

    public static class CreditCardBuilderImpl implements CreditCardBuilder {
        private long id;
        private String name;
        private String pincode; //hashed value of 4-digit number
        private long balance;
        private String activityStatus;

        @Override
        public CreditCardBuilder setId(long id) {
            this.id = id;
            return this;
        }

        @Override
        public CreditCardBuilder setName(String name) {
            this.name = name;
            return this;
        }

        @Override
        public CreditCardBuilder setPincode(String pincode) {
            this.pincode = pincode;
            return this;
        }

        @Override
        public CreditCardBuilder setBalance(long balance) {
            this.balance = balance;
            return this;
        }

        @Override
        public CreditCardBuilder setActivityStatus(String activityStatus) {
            this.activityStatus = activityStatus;
            return this;
        }

        @Override
        public CreditCard build() {
            return new CreditCard(this);
        }
    }

    public CreditCard() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public String getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(String activityStatus) {
        this.activityStatus = activityStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditCard that = (CreditCard) o;
        return balance == that.balance && Objects.equals(name, that.name) && Objects.equals(pincode, that.pincode) && Objects.equals(activityStatus, that.activityStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, pincode, balance, activityStatus);
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "name='" + name + '\'' +
                ", pincode='" + pincode + '\'' +
                ", balance=" + balance +
                ", activityStatus='" + activityStatus + '\'' +
                '}';
    }

}
