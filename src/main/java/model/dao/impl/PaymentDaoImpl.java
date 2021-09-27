package model.dao.impl;

import model.dao.PaymentDao;
import model.dao.connection.Connector;
import model.dao.constants.LogInfo;
import model.dao.constants.SQLConstants;
import model.entity.Payment;
import model.exception.NotFoundPaymentException;
import model.exception.NotFoundUserException;
import org.apache.log4j.Logger;


import javax.naming.NamingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class PaymentDaoImpl implements PaymentDao {

    private static final Logger logger = Logger.getLogger(PaymentDaoImpl.class);

    @Override
    public boolean add(Payment entity) throws NamingException, SQLException {
        logger.info(LogInfo.ADD + LogInfo.STARTED);
        Connection connection = Connector.getInstance().getConnection();
        connection.setAutoCommit(false);
        try (PreparedStatement statement =
                     connection.prepareStatement(SQLConstants.INSERT_PAYMENT.getConstant())) {
            statement.setLong(1, entity.getAmount());
            statement.setTimestamp(2, new Timestamp(entity.getDate().getTime()));
            statement.setString(3, entity.getDescription());
            statement.setString(4, entity.getPaymentStatus());
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
    public Payment getById(Long id) throws NamingException, SQLException {
        logger.info(LogInfo.GET_BY_ID + id + LogInfo.STARTED);
        Payment payment;
        try (Connection connection = Connector.getInstance().getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SQLConstants.GET_PAYMENT_BY_ID.getConstant())) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            List<Payment> payments = initPaymentList(resultSet);
            if (!payments.isEmpty()) {
                payment = payments.get(0);
            } else {
                logger.info("payment not found");
                throw new NotFoundPaymentException();
            }

        }
        logger.info(LogInfo.GET_BY_ID + id + LogInfo.SUCCESS);
        return payment;
    }

    @Override
    public List<Payment> getAll() throws NamingException {
        logger.info(LogInfo.GET_ALL + LogInfo.STARTED);
        List<Payment> payments;
        try (Connection connection = Connector.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(SQLConstants.GET_ALL_PAYMENTS.getConstant())) {
            payments = initPaymentList(rs);
        } catch (SQLException e) {
            logger.info("method getAll() failed. Returning empty list");
            return Collections.emptyList();
        }
        logger.info(LogInfo.GET_ALL + LogInfo.SUCCESS);
        return payments;
    }

    @Override
    public boolean updateEntity(Payment entity) throws NamingException, SQLException{
        logger.info(LogInfo.UPDATE + entity + LogInfo.STARTED);
        Connection connection = Connector.getInstance().getConnection();
        connection.setAutoCommit(false);
        try (PreparedStatement statement =
                     connection.prepareStatement(SQLConstants.UPDATE_PAYMENT.getConstant())) {
            statement.setLong(1, entity.getAmount());
            statement.setTimestamp(2, new Timestamp(entity.getDate().getTime()));
            statement.setString(3, entity.getDescription());
            statement.setString(4, entity.getPaymentStatus());
            statement.setLong(5, entity.getId());
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
                     connection.prepareStatement(SQLConstants.DELETE_PAYMENT_BY_ID.getConstant())) {
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

    private List<Payment> initPaymentList(ResultSet rs) throws SQLException {
        List<Payment> payments  = new ArrayList<>();
        while (rs.next()) {

            Payment payment = new Payment.PaymentBuilderImpl()
                    .setId(rs.getLong(1))
                    .setAmount(rs.getLong(2))
                    .setDate(rs.getTimestamp(3))
                    .setDescription(rs.getString(4))
                    .setPaymentStatus(rs.getString(5))
                    .build();

            payments.add(payment);
        }
        return payments;
    }

}
