package model.dao.impl;

import model.dao.CreditCardDao;
import model.dao.connection.Connector;
import model.dao.constants.LogInfo;
import model.dao.constants.SQLConstants;
import model.entity.CreditCard;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CreditCardDaoImpl implements CreditCardDao  {

    private static final Logger logger = Logger.getLogger(CreditCardDaoImpl.class);

    @Override
    public boolean add(CreditCard entity) throws NamingException {
        logger.info(LogInfo.ADD + LogInfo.STARTED);

        try (Connection connection = Connector.getInstance().getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SQLConstants.INSERT_CREDIT_CARD.getConstant())) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getPincode());
            statement.setLong(3, entity.getBalance());
            statement.setString(4, entity.getActivityStatus());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(LogInfo.ADD + LogInfo.FAILED, e.getCause());
            return false;
        }
        logger.info(LogInfo.ADD + LogInfo.SUCCESS);
        return true;
    }

    @Override
    public CreditCard getById(Long id) throws NamingException, SQLException {
        logger.info(LogInfo.GET_BY_ID + id + LogInfo.STARTED);
        CreditCard creditCard;
        try (Connection connection = Connector.getInstance().getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SQLConstants.GET_CREDIT_CARD_BY_ID.getConstant())) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            List<CreditCard> creditCards = initCreditCardList(resultSet);
            if (!creditCards.isEmpty()) {
                creditCard = creditCards.get(0);
            } else {
                logger.warn("Credit card by id wasn't found. Returning null");
                return null;
            }

        }
        logger.info(LogInfo.GET_BY_ID + id + LogInfo.SUCCESS);
        return creditCard;
    }

    @Override
    public List<CreditCard> getAll() throws NamingException, SQLException {
        logger.info(LogInfo.GET_ALL + LogInfo.STARTED);
        List<CreditCard> creditCards;

        try (Connection connection = Connector.getInstance().getConnection();
             Statement statement = connection.createStatement()){

            ResultSet rs = statement.executeQuery(SQLConstants.GET_ALL_CARDS.getConstant());
            creditCards = initCreditCardList(rs);

        }
        logger.info(LogInfo.GET_ALL + LogInfo.SUCCESS);
        return creditCards;
    }

    @Override
    public boolean updateEntity(CreditCard entity) throws NamingException{
        logger.info(LogInfo.UPDATE + entity + LogInfo.STARTED);

        try (Connection connection = Connector.getInstance().getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SQLConstants.UPDATE_CREDIT_CARD.getConstant())) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getPincode());
            statement.setLong(3, entity.getBalance());
            statement.setString(4, entity.getActivityStatus());
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
                     connection.prepareStatement(SQLConstants.DELETE_CREDIT_CARD_BY_ID.getConstant())) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(LogInfo.DELETE + id + LogInfo.FAILED, e.getCause());
            return false;
        }
        logger.info(LogInfo.DELETE + id + LogInfo.SUCCESS);
        return true;
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
