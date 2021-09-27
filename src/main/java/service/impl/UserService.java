package service.impl;

import model.dao.UserDao;
import model.dao.factory.DaoFactory;
import model.entity.User;
import model.exception.DataBaseException;
import model.exception.NotFoundUserException;
import model.exception.ServiceException;
import org.apache.log4j.Logger;
import service.IUserService;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserService implements IUserService {

    private static final Logger logger = Logger.getLogger(UserService.class);

    private final DaoFactory daoFactory = DaoFactory.getInstance();
    private final UserDao userDao = daoFactory.getUserDAO();


    @Override
    public List<User> getAll() throws NamingException {
        try {
            return userDao.getAll().stream()
                    .filter(p -> p.getRole().equals("user"))
                    .collect(Collectors.toList());
        } catch (DataBaseException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User getById(Long id) throws SQLException, NamingException {
        try {
            return userDao.getById(id);
        } catch (DataBaseException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean add(User entity) throws SQLException, NamingException {
        try {
            return userDao.add(entity);
        } catch (DataBaseException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateEntity(User entity) throws SQLException, NamingException {
        try {
            return userDao.updateEntity(entity);
        } catch (DataBaseException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean deleteEntity(Long id) throws SQLException, NamingException {
        try {
            return userDao.deleteEntity(id);
        } catch (DataBaseException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User getUserByName(String name) throws NamingException {
        try {
            return userDao.getUserByName(name);
        } catch (DataBaseException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User getUserByEmail(String email) throws NamingException {
        try {
            return userDao.getUserByEmail(email);
        } catch (DataBaseException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Execute SELECT query.t_user_cards
     * Search User info in database ISPManager by login and password.
     * Then generate java object and return as result.
     *
     * @param login    - login column name in t_user table.
     * @param password - password column name in t_user table.
     * @return - User object which was generated from found info.
     * @throws NamingException - in case of having troubles with connection.
     */
    @Override
    public User getByLoginAndPass(String login, String password) throws NamingException {

        User user = userDao.getUserByEmail(login);
        if (Objects.isNull(user)) {
            logger.info("LOGIN");
            throw new NotFoundUserException();
        }

        if (!password.equals(user.getPassword())) {
            logger.info("PASSWORD");
            throw new NotFoundUserException();
        }
        return user;
    }

}
