package service.impl;

import model.dao.UserDao;
import model.dao.constants.LogInfo;
import model.dao.factory.DaoFactory;
import model.entity.CreditCard;
import model.entity.Payment;
import model.entity.User;
import model.exception.DataBaseException;
import org.apache.log4j.Logger;
import service.UserService;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    private static final Logger logger = Logger.getLogger(UserServiceImpl.class);

    private final DaoFactory daoFactory = DaoFactory.getInstance();
    private final UserDao userDao = daoFactory.getUserDAO();


    @Override
    public boolean add(User entity) throws NamingException {
        return userDao.add(entity);
    }

    @Override
    public boolean addPaymentToUser(Long userId, Long paymentId) throws NamingException {
        return userDao.addPaymentToUser(userId, paymentId);
    }

    @Override
    public boolean addCardToUser(Long userId, Long cardId) throws NamingException {
        return userDao.addCardToUser(userId, cardId);
    }

    @Override
    public List<User> getAll() throws NamingException, DataBaseException {
        try {
            return userDao.getAll().stream()
                    .filter(p -> p.getRole().equals("user"))
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            logger.error(LogInfo.GET_ALL + LogInfo.FAILED, e.getCause());
            throw new DataBaseException(e);
        }
    }

    @Override
    public User getById(Long id) throws NamingException, DataBaseException {
        try {
            return userDao.getById(id);
        } catch (SQLException e) {
            logger.error(LogInfo.GET_BY_ID + LogInfo.FAILED, e.getCause());
            throw new DataBaseException(e);
        }
    }

    @Override
    public User getUserByName(String name) throws NamingException, DataBaseException {
        try {
            return userDao.getUserByName(name);
        } catch (SQLException e) {
            logger.error(LogInfo.GET_USER_BY_NAME + name + LogInfo.FAILED, e.getCause());
            throw new DataBaseException(e);
        }
    }

    @Override
    public User getUserByEmail(String email) throws NamingException, DataBaseException {
        try {
            return userDao.getUserByEmail(email);
        } catch (SQLException e) {
            logger.error(LogInfo.GET_USER_BY_EMAIL + email + LogInfo.FAILED, e.getCause());
            throw new DataBaseException(e);
        }
    }

    @Override
    public User getUserByCardId(Long cardId) throws NamingException, DataBaseException {
        try {
            return userDao.getUserByCardId(cardId);
        } catch (SQLException e) {
            logger.error(LogInfo.GET_USER_BY_CARD_ID + cardId + LogInfo.FAILED, e.getCause());
            throw new DataBaseException(e);
        }
    }

    @Override
    public List<CreditCard> getAllUserCards(User user) throws NamingException, DataBaseException {
        try {
            return userDao.getAllUserCards(user);
        } catch (SQLException e) {
            logger.error(LogInfo.GET_ALL_USER_CARDS + user.getId() + LogInfo.FAILED, e.getCause());
            throw new DataBaseException(e);
        }
    }

    @Override
    public List<Payment> getAllUserPayments(User user) throws NamingException, DataBaseException {
        try {
            return userDao.getAllUserPayments(user);
        } catch (SQLException e) {
            logger.error(LogInfo.GET_ALL_USER_PAYMENTS + user.getId() + LogInfo.FAILED, e.getCause());
            throw new DataBaseException(e);
        }
    }

    @Override
    public List<Payment> getAllPaymentsFromBeginToEndTime(User user, Long beginTime, Long endTime) throws NamingException, DataBaseException {
        try {
            return userDao.getAllUserPayments(user).stream()
                    .filter(p -> (p.getDate().getTime() >= beginTime && p.getDate().getTime() <= endTime))
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            logger.error(LogInfo.GET_ALL_USER_CARDS + user.getId() + LogInfo.FAILED, e.getCause());
            throw new DataBaseException(e);
        }
    }

    @Override
    public List<CreditCard> getAllUserActiveCards(User user) throws NamingException, DataBaseException {
        try {
            return userDao.getAllUserCards(user).stream()
                    .filter(p -> p.getActivityStatus().equals("active"))
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            logger.error(LogInfo.GET_ALL_USER_CARDS + user.getId() + LogInfo.FAILED, e.getCause());
            throw new DataBaseException(e);
        }
    }

    @Override
    public boolean updateEntity(User entity) throws NamingException {
        return userDao.updateEntity(entity);
    }

    @Override
    public boolean deleteEntity(Long id) throws NamingException {
        return userDao.deleteEntity(id);
    }

    @Override
    public boolean checkIfNameExists(String name) throws NamingException, DataBaseException {
        try {
            if (Objects.nonNull(userDao.getUserByName(name))){
                return true;
            }
        } catch (SQLException e) {
            logger.error(LogInfo.GET_USER_BY_NAME + name + LogInfo.FAILED, e.getCause());
            throw new DataBaseException(e);
        }
        return false;
    }

    @Override
    public boolean checkIfEmailExists(String email) throws NamingException, DataBaseException {
        try {
            if (Objects.nonNull(userDao.getUserByEmail(email))){
                return true;
            }
        } catch (SQLException e) {
            logger.error(LogInfo.GET_USER_BY_EMAIL + email + LogInfo.FAILED, e.getCause());
            throw new DataBaseException(e);
        }
        return false;
    }

}
