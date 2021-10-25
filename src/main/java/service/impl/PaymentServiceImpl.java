package service.impl;

import model.dao.PaymentDao;
import model.dao.constants.LogInfo;
import model.dao.factory.DaoFactory;
import model.entity.Payment;
import model.entity.User;
import model.exception.DataBaseException;
import org.apache.log4j.Logger;
import service.PaymentService;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.List;

public class PaymentServiceImpl implements PaymentService {

    private static final Logger logger = Logger.getLogger(PaymentServiceImpl.class);

    private final DaoFactory daoFactory = DaoFactory.getInstance();
    private final PaymentDao paymentDao = daoFactory.getPaymentDAO();

    @Override
    public boolean add(Payment entity) throws NamingException {
        return paymentDao.add(entity);
    }

    @Override
    public List<Payment> getAll() throws NamingException, DataBaseException {
        try {
            return paymentDao.getAll();
        } catch (SQLException e) {
            logger.error(LogInfo.GET_ALL + LogInfo.FAILED, e.getCause());
            throw new DataBaseException(e);
        }
    }

    @Override
    public Payment getById(Long id) throws NamingException, DataBaseException {
        try {
            return paymentDao.getById(id);
        } catch (SQLException e) {
            logger.error(LogInfo.GET_BY_ID + LogInfo.FAILED, e.getCause());
            throw new DataBaseException(e);
        }
    }

    @Override
    public Long getIdOfPayment(Payment payment) throws NamingException, DataBaseException {
        try {
            return paymentDao.getIdOfPayment(payment);
        } catch (SQLException e) {
            logger.error(LogInfo.GET_ID_OF_PAYMENT + LogInfo.FAILED, e.getCause());
            throw new DataBaseException(e);
        }
    }

    @Override
    public boolean updateEntity(Payment entity) throws NamingException {
        return paymentDao.updateEntity(entity);
    }

    @Override
    public boolean deleteEntity(Long id) throws NamingException {
        return paymentDao.deleteEntity(id);
    }

    @Override
    public String formDescription(User sender, Long senderCardId, User receiver, Long receiverCardId) {
        return "From " + sender.getName() + " card with id=" + senderCardId
                + " to " + receiver.getName() + " card with id=" + receiverCardId;
    }
}
