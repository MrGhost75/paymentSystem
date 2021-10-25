package model.dao.impl;

import model.dao.PaymentDao;
import model.dao.connection.Connector;
import model.dao.constants.LogInfo;
import model.dao.constants.SQLConstants;
import model.entity.Payment;
import org.apache.log4j.Logger;


import javax.naming.NamingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class PaymentDaoImpl implements PaymentDao {

    private static final Logger logger = Logger.getLogger(PaymentDaoImpl.class);

    @Override
    public boolean add(Payment entity) throws NamingException {
        logger.info(LogInfo.ADD + LogInfo.STARTED);

        try (Connection connection = Connector.getInstance().getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SQLConstants.INSERT_PAYMENT.getConstant())) {
            statement.setLong(1, entity.getAmount());
            statement.setTimestamp(2, new Timestamp(entity.getDate().getTime()));
            statement.setString(3, entity.getDescription());
            statement.setString(4, entity.getPaymentStatus());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(LogInfo.ADD + LogInfo.FAILED, e.getCause());
            return false;
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
                logger.warn("Payment by id wasn't found. Returning null");
                return null;
            }

        }
        logger.info(LogInfo.GET_BY_ID + id + LogInfo.SUCCESS);
        return payment;
    }

    @Override
    public List<Payment> getAll() throws NamingException, SQLException {
        logger.info(LogInfo.GET_ALL + LogInfo.STARTED);
        List<Payment> payments;
        try (Connection connection = Connector.getInstance().getConnection();
            Statement statement = connection.createStatement()) {

            ResultSet rs = statement.executeQuery(SQLConstants.GET_ALL_PAYMENTS.getConstant());
            payments = initPaymentList(rs);
        }
        logger.info(LogInfo.GET_ALL + LogInfo.SUCCESS);
        return payments;
    }

    @Override
    public Long getIdOfPayment(Payment payment) throws NamingException, SQLException {
        logger.info(LogInfo.GET_ID_OF_PAYMENT + payment + LogInfo.STARTED);
        long idOfPayment = 0;
        try (Connection connection = Connector.getInstance().getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SQLConstants.SELECT_ID_OF_PAYMENT.getConstant())) {

            statement.setLong(1, payment.getAmount());
            statement.setString(2, payment.getDescription());
            statement.setString(3, payment.getPaymentStatus());

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                if (Math.abs(rs.getTimestamp(3).getTime() - payment.getDate().getTime()) < 2000) {
                    idOfPayment = rs.getLong(1);
                }
            }

        }
        logger.info(LogInfo.GET_ID_OF_PAYMENT + payment + LogInfo.SUCCESS);
        return idOfPayment;
    }

    @Override
    public boolean updateEntity(Payment entity) throws NamingException {
        logger.info(LogInfo.UPDATE + entity + LogInfo.STARTED);
        try (Connection connection = Connector.getInstance().getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SQLConstants.UPDATE_PAYMENT.getConstant())) {
            statement.setLong(1, entity.getAmount());
            statement.setTimestamp(2, new Timestamp(entity.getDate().getTime()));
            statement.setString(3, entity.getDescription());
            statement.setString(4, entity.getPaymentStatus());
            statement.setLong(5, entity.getId());
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
                     connection.prepareStatement(SQLConstants.DELETE_PAYMENT_BY_ID.getConstant())) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(LogInfo.DELETE + id + LogInfo.FAILED, e.getCause());
            return false;
        }
        logger.info(LogInfo.DELETE + id + LogInfo.SUCCESS);
        return true;
    }

    static List<Payment> initPaymentList(ResultSet rs) throws SQLException {
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
