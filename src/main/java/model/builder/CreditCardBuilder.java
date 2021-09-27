package model.builder;

import model.entity.CreditCard;

public interface CreditCardBuilder {
    CreditCardBuilder setId(long id);
    CreditCardBuilder setName(String name);
    CreditCardBuilder setPincode(String pincode);
    CreditCardBuilder setBalance(long balance);
    CreditCardBuilder setActivityStatus(String activityStatus);
    CreditCard build();
}
