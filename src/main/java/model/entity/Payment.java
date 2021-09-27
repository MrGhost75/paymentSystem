package model.entity;

import model.builder.PaymentBuilder;

import java.util.Date;
import java.util.Objects;

public class Payment extends Model {
    private long amount;
    private Date date;
    private String description;
    private String paymentStatus;

    private Payment(PaymentBuilderImpl builder) {
        super(builder.id);
        this.amount = builder.amount;
        this.date = builder.date;
        this.description = builder.description;
        this.paymentStatus = builder.paymentStatus;
    }

    public static class PaymentBuilderImpl implements PaymentBuilder {

        private long id;
        private long amount;
        private Date date;
        private String description;
        private String paymentStatus;

        @Override
        public PaymentBuilder setId(long id) {
            this.id = id;
            return this;
        }

        @Override
        public PaymentBuilder setAmount(long amount) {
            this.amount = amount;
            return this;
        }

        @Override
        public PaymentBuilder setDate(Date date) {
            this.date = date;
            return this;
        }

        @Override
        public PaymentBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        @Override
        public PaymentBuilder setPaymentStatus(String paymentStatus) {
            this.paymentStatus = paymentStatus;
            return this;
        }

        @Override
        public Payment build() {
            return new Payment(this);
        }
    }

    public Payment() {

    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return amount == payment.amount && Objects.equals(date, payment.date) && Objects.equals(description, payment.description) && Objects.equals(paymentStatus, payment.paymentStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, date, description, paymentStatus);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "amount=" + amount +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                '}';
    }

}
