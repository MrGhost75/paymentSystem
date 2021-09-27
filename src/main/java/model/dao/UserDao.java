package model.dao;

import model.entity.User;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.Optional;

public interface UserDao  extends CrudDao<Long, User> {

    User getUserByName(String name) throws NamingException;

    User getUserByEmail(String email) throws NamingException;

}
