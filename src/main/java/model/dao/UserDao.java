package model.dao;

import model.entity.CreditCard;
import model.entity.Payment;
import model.entity.User;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.List;

public interface UserDao  extends CrudDao<Long, User> {

    boolean addPaymentToUser(Long userId, Long paymentId) throws NamingException;

    boolean addCardToUser(Long userId, Long cardId) throws NamingException;

    User getUserByName(String name) throws NamingException, SQLException;

    User getUserByEmail(String email) throws NamingException, SQLException;

    User getUserByCardId(Long cardId) throws NamingException, SQLException;

    List<CreditCard> getAllUserCards(User user) throws NamingException, SQLException;

    List<Payment> getAllUserPayments(User user) throws NamingException, SQLException;
}
