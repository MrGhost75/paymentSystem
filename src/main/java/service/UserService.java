package service;

import model.entity.CreditCard;
import model.entity.Payment;
import model.entity.User;
import model.exception.DataBaseException;

import javax.naming.NamingException;
import java.util.List;


public interface UserService extends CrudService<Long, User> {

    boolean addPaymentToUser(Long userId, Long paymentId) throws NamingException;

    boolean addCardToUser(Long userId, Long cardId) throws NamingException;

    User getUserByName(String name) throws NamingException, DataBaseException;

    User getUserByEmail(String email) throws NamingException, DataBaseException;

    User getUserByCardId(Long cardId) throws NamingException, DataBaseException;

    List<CreditCard> getAllUserCards(User user) throws NamingException, DataBaseException;

    List<Payment> getAllUserPayments(User user) throws NamingException, DataBaseException;

    List<Payment> getAllPaymentsFromBeginToEndTime(User user, Long beginTime, Long endTime) throws NamingException, DataBaseException;

    List<CreditCard> getAllUserActiveCards(User user) throws NamingException, DataBaseException;

    boolean checkIfNameExists(String name) throws NamingException, DataBaseException;

    boolean checkIfEmailExists(String name) throws NamingException, DataBaseException;
}
