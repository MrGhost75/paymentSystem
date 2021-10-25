package service;

import model.entity.Payment;
import model.entity.User;
import model.exception.DataBaseException;

import javax.naming.NamingException;

public interface PaymentService extends CrudService<Long, Payment> {
    String formDescription(User sender, Long senderCardId, User receiver, Long receiverCardId);

    Long getIdOfPayment(Payment payment) throws NamingException, DataBaseException;
}
