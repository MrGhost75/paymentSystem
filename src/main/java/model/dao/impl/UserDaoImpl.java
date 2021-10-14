package model.dao.impl;

import model.dao.UserDao;
import model.dao.connection.Connector;
import model.dao.constants.LogInfo;
import model.dao.constants.SQLConstants;
import model.entity.CreditCard;
import model.entity.User;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDaoImpl implements UserDao {

    private static final Logger logger = Logger.getLogger(UserDaoImpl.class);

    @Override
    public boolean add(User entity) throws NamingException {
        logger.info(LogInfo.ADD + LogInfo.STARTED);

        try (Connection connection = Connector.getInstance().getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SQLConstants.INSERT_USER.getConstant())) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getEmail());
            statement.setString(3, entity.getPassword());
            statement.setString(4, entity.getRole());
            statement.setString(5, entity.getActivityStatus());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(LogInfo.ADD + LogInfo.FAILED, e.getCause());
            return false;
        }
        logger.info(LogInfo.ADD + LogInfo.SUCCESS);
        return true;
    }

    @Override
    public User getById(Long id) throws NamingException, SQLException {
        logger.info(LogInfo.GET_BY_ID + id + LogInfo.STARTED);
        User user;
        try (Connection connection = Connector.getInstance().getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SQLConstants.GET_USER_BY_ID.getConstant())) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            List<User> users = initUserList(resultSet);
            if (!users.isEmpty()) {
                user = users.get(0);
            } else {
                logger.warn("User by id wasn't found. Returning null");
                return null;
            }

        }
        logger.info(LogInfo.GET_BY_ID + id + LogInfo.SUCCESS);
        return user;
    }

    @Override
    public List<User> getAll() throws NamingException, SQLException {
        logger.info(LogInfo.GET_ALL + LogInfo.STARTED);
        List<User> users;
        try (Connection connection = Connector.getInstance().getConnection();
            Statement statement = connection.createStatement()) {

            ResultSet rs = statement.executeQuery(SQLConstants.GET_ALL_USERS.getConstant());
            users = initUserList(rs);

        }
        logger.info(LogInfo.GET_ALL + LogInfo.SUCCESS);
        return users;
    }

    /**
     * Execute SELECT query.
     * Get row from t_user table in database paymentsystem with name @param
     *
     * @param name - name column in t_user table
     * @return - User object which was generated from found info.
     * @throws NamingException - in case of having troubles with getting connector object using JNDI.
     * @throws SQLException - in case of having troubles with connecting to database.
     */
    @Override
    public User getUserByName(String name) throws NamingException, SQLException {
        logger.info(LogInfo.GET_USER_BY_NAME + name + LogInfo.STARTED);
        User user;

        try (Connection connection = Connector.getInstance().getConnection();
             PreparedStatement statement =
                    connection.prepareStatement(SQLConstants.SELECT_USER_BY_NAME.getConstant())) {

            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            List<User> users = initUserList(resultSet);
            if (!users.isEmpty()) {
                user = users.get(0);
            } else {
                logger.warn("User by name wasn't found. Returning null");
                return null;
            }

        }
        logger.info(LogInfo.GET_USER_BY_NAME + name + LogInfo.SUCCESS);
        return user;
    }

    /**
     * Execute SELECT query.
     * Get row from t_user table in database paymentsystem with email @param
     *
     * @param email - email column in t_user table
     * @return - User object which was generated from found info.
     * @throws NamingException - in case of having troubles with getting connector object using JNDI.
     * @throws SQLException - in case of having troubles with connecting to database.
     */
    @Override
    public User getUserByEmail(String email) throws NamingException, SQLException {
        logger.info(LogInfo.GET_USER_BY_EMAIL + email + LogInfo.STARTED);
        User user;
        try (Connection connection = Connector.getInstance().getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SQLConstants.SELECT_USER_BY_EMAIL.getConstant())) {

            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            List<User> users = initUserList(resultSet);
            if (!users.isEmpty()) {
                user = users.get(0);
            } else {
                logger.warn("User by email wasn't found. Returning null");
                return null;
            }
        }
        logger.info(LogInfo.GET_USER_BY_EMAIL + email + LogInfo.SUCCESS);
        return user;
    }

    /**
     * Execute SELECT query.
     * Get all credit cards from t_credit_card table in database paymentsystem
     * using connection between User's id and his cards' ids in t_user_cards table
     *
     * @param user - User object (row in t_user table)
     * @return - List of Credit Cards which was generated from found info.
     * @throws NamingException - in case of having troubles with getting connector object using JNDI.
     * @throws SQLException - in case of having troubles with connecting to database.
     */
    @Override
    public List<CreditCard> getAllUserCards(User user) throws NamingException, SQLException {
        logger.info(LogInfo.GET_ALL_USER_CARDS + user.getId() + LogInfo.STARTED);
        List<CreditCard> creditCards;
        try (Connection connection = Connector.getInstance().getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SQLConstants.SELECT_ALL_USER_CARDS.getConstant())) {

            statement.setLong(1, user.getId());
            ResultSet resultSet = statement.executeQuery();
            creditCards = initCreditCardList(resultSet);

        }
        logger.info(LogInfo.GET_ALL_USER_CARDS + LogInfo.SUCCESS);
        return creditCards;
    }

    @Override
    public boolean updateEntity(User entity) throws NamingException {
        logger.info(LogInfo.UPDATE + entity + LogInfo.STARTED);
        try (Connection connection = Connector.getInstance().getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SQLConstants.UPDATE_USER.getConstant())) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getEmail());
            statement.setString(3, entity.getPassword());
            statement.setString(4, entity.getRole());
            statement.setString(5, entity.getActivityStatus());
            statement.setLong(6, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(LogInfo.UPDATE + entity + LogInfo.FAILED, e.getCause());
            return false;
        }
        logger.info(LogInfo.UPDATE + entity + LogInfo.SUCCESS);
        return true;
    }

    @Override
    public boolean deleteEntity(Long id) throws NamingException {
        logger.info(LogInfo.DELETE + id + LogInfo.STARTED);

        try (Connection connection = Connector.getInstance().getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SQLConstants.DELETE_USER_BY_ID.getConstant())) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(LogInfo.DELETE + id + LogInfo.FAILED, e.getCause());
            return false;
        }
        logger.info(LogInfo.DELETE + id + LogInfo.SUCCESS);
        return true;
    }

    private List<User> initUserList(ResultSet rs) throws SQLException {
        List<User> users = new ArrayList<>();
        while (rs.next()) {
            User user = new User.UserBuilderImpl()
                    .setId(rs.getLong(1))
                    .setName(rs.getString(2))
                    .setEmail(rs.getString(3))
                    .setPassword(rs.getString(4))
                    .setRole(rs.getString(5))
                    .setActivityStatus(rs.getString(6))
                    .build();
            users.add(user);
        }
        return users;
    }

    List<CreditCard> initCreditCardList(ResultSet rs) throws SQLException {
        List<CreditCard> creditCards  = new ArrayList<>();
        while (rs.next()) {
            CreditCard creditCard = new CreditCard.CreditCardBuilderImpl()
                    .setId(rs.getLong(1))
                    .setName(rs.getString(2))
                    .setPincode(rs.getString(3))
                    .setBalance(rs.getLong(4))
                    .setActivityStatus(rs.getString(5))
                    .build();
            creditCards.add(creditCard);
        }
        return creditCards;
    }

}
