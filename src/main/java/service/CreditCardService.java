package service;

import model.entity.CreditCard;
import model.exception.DataBaseException;

import javax.naming.NamingException;

public interface CreditCardService extends CrudService<Long, CreditCard> {

    boolean checkIfIdExists(Long id) throws NamingException, DataBaseException;

    CreditCard getCardByName(String name) throws NamingException, DataBaseException;

    boolean checkIfNameExists(String name) throws NamingException, DataBaseException;

}
