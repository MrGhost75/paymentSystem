package model.dao;

import model.entity.CreditCard;
import model.entity.User;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.List;

public interface UserDao  extends CrudDao<Long, User> {

    User getUserByName(String name) throws NamingException, SQLException;

    User getUserByEmail(String email) throws NamingException, SQLException;

    List<CreditCard> getAllUserCards(User user) throws NamingException, SQLException;
}
