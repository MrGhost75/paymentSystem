package model.dao;

import model.entity.CreditCard;

import javax.naming.NamingException;
import java.sql.SQLException;

public interface CreditCardDao  extends CrudDao<Long, CreditCard> {
    CreditCard getCardByName(String name) throws NamingException, SQLException;
}
