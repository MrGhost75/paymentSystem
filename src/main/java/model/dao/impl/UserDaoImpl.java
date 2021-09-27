package model.dao.impl;

import model.dao.UserDao;
import model.dao.connection.Connector;
import model.dao.constants.LogInfo;
import model.dao.constants.SQLConstants;
import model.entity.User;
import model.exception.NotFoundUserException;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


public class UserDaoImpl implements UserDao {

    private static final Logger logger = Logger.getLogger(UserDaoImpl.class);

    @Override
    public boolean add(User entity) throws NamingException, SQLException {
        logger.info(LogInfo.ADD + LogInfo.STARTED);
        Connection connection = Connector.getInstance().getConnection();
        connection.setAutoCommit(false);
        try (PreparedStatement statement =
                     connection.prepareStatement(SQLConstants.INSERT_USER.getConstant())) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getEmail());
            statement.setString(3, entity.getPassword());
            statement.setString(4, entity.getRole());
            statement.setString(5, entity.getActivityStatus());
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            logger.info(LogInfo.ADD + LogInfo.ROLLBACK);
            connection.rollback();
            return false;
        } finally {
            logger.info(LogInfo.CLOSE_CONNECTION);
            connection.close();
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
                logger.info("user not found");
                throw new NotFoundUserException();
            }

        }
        logger.info(LogInfo.GET_BY_ID + id + LogInfo.SUCCESS);
        return user;
    }

    @Override
    public List<User> getAll() throws NamingException {
        logger.info(LogInfo.GET_ALL + LogInfo.STARTED);
        List<User> users;
        try (Connection connection = Connector.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(SQLConstants.GET_ALL_USERS.getConstant())) {
            users = initUserList(rs);
        } catch (SQLException e) {
            logger.info("method getAll() failed. Returning empty list");
            return Collections.emptyList();
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
     * @throws NamingException - in case of having troubles with connection.
     */
    @Override
    public User getUserByName(String name) throws NamingException {
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
                logger.info("user not found");
                throw new NotFoundUserException();
            }

        } catch (SQLException e) {
            logger.info(LogInfo.GET_USER_BY_NAME + name + " failed.");
            throw new NotFoundUserException();
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
     * @throws NamingException - in case of having troubles with connection.
     */
    @Override
    public User getUserByEmail(String email) throws NamingException {
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
                logger.info("user not found");
                throw new NotFoundUserException();
            }

        } catch (SQLException e) {
            logger.info(LogInfo.GET_USER_BY_EMAIL + email + " failed.");
            throw new NotFoundUserException();
        }
        logger.info(LogInfo.GET_USER_BY_EMAIL + email + LogInfo.SUCCESS);
        return user;
    }

    @Override
    public boolean updateEntity(User entity) throws NamingException, SQLException{
        logger.info(LogInfo.UPDATE + entity + LogInfo.STARTED);
        Connection connection = Connector.getInstance().getConnection();
        connection.setAutoCommit(false);
        try (PreparedStatement statement =
                     connection.prepareStatement(SQLConstants.UPDATE_USER.getConstant())) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getEmail());
            statement.setString(3, entity.getPassword());
            statement.setString(4, entity.getRole());
            statement.setString(5, entity.getActivityStatus());
            statement.setLong(6, entity.getId());
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            logger.info(LogInfo.UPDATE + entity + LogInfo.ROLLBACK);
            connection.rollback();
            return false;

        } finally {
            logger.info(LogInfo.CLOSE_CONNECTION);
            connection.close();
        }
        logger.info(LogInfo.UPDATE + entity + LogInfo.SUCCESS);
        return true;
    }

    @Override
    public boolean deleteEntity(Long id) throws NamingException, SQLException {
        logger.info(LogInfo.DELETE + id + LogInfo.STARTED);
        Connection connection = Connector.getInstance().getConnection();
        connection.setAutoCommit(false);
        try (PreparedStatement statement =
                     connection.prepareStatement(SQLConstants.DELETE_USER_BY_ID.getConstant())) {
            statement.setLong(1, id);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            logger.info(LogInfo.DELETE + id + LogInfo.ROLLBACK);
            connection.rollback();
            return false;
        } finally {
            logger.info(LogInfo.CLOSE_CONNECTION);
            connection.close();
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

}
