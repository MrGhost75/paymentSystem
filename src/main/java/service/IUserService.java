package service;

import model.entity.User;

import javax.naming.NamingException;
import java.sql.SQLException;


public interface IUserService extends CrudService<Long, User> {

    User getUserByName(String name) throws NamingException;

    User getUserByEmail(String email) throws NamingException;

    User getByLoginAndPass(String login, String password) throws NamingException;

}
