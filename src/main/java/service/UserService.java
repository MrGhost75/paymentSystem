package service;

import model.entity.CreditCard;
import model.entity.User;
import model.exception.DataBaseException;

import javax.naming.NamingException;
import java.util.List;


public interface UserService extends CrudService<Long, User> {

    User getUserByName(String name) throws NamingException, DataBaseException;

    User getUserByEmail(String email) throws NamingException, DataBaseException;

    List<CreditCard> getAllUserCards(User user) throws NamingException, DataBaseException;

    boolean checkIfNameExists(String name) throws NamingException, DataBaseException;

    boolean checkIfEmailExists(String name) throws NamingException, DataBaseException;
}
