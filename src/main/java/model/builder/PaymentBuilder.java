package model.builder;

import model.entity.Payment;

import java.util.Date;

public interface PaymentBuilder {
    PaymentBuilder setId(long id);
    PaymentBuilder setAmount(long amount);
    PaymentBuilder setDate(Date date);
    PaymentBuilder setDescription(String description);
    PaymentBuilder setPaymentStatus(String paymentStatus);
    Payment build();
}
