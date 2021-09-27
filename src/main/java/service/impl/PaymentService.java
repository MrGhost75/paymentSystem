package service.impl;

import model.dao.PaymentDao;
import model.dao.factory.DaoFactory;
import model.entity.Payment;
import model.exception.DataBaseException;
import model.exception.ServiceException;
import org.apache.log4j.Logger;
import service.IPaymentService;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.List;

public class PaymentService implements IPaymentService {

    private static final Logger logger = Logger.getLogger(PaymentService.class);

    private final DaoFactory daoFactory = DaoFactory.getInstance();
    private final PaymentDao paymentDao = daoFactory.getPaymentDAO();


    @Override
    public List<Payment> getAll() throws NamingException {
        try {
            return paymentDao.getAll();
        } catch (DataBaseException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Payment getById(Long id) throws SQLException, NamingException {
        try {
            return paymentDao.getById(id);
        } catch (DataBaseException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean add(Payment entity) throws SQLException, NamingException {
        try {
            return paymentDao.add(entity);
        } catch (DataBaseException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateEntity(Payment entity) throws SQLException, NamingException {
        try {
            return paymentDao.updateEntity(entity);
        } catch (DataBaseException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean deleteEntity(Long id) throws SQLException, NamingException {
        try {
            return paymentDao.deleteEntity(id);
        } catch (DataBaseException e) {
            throw new ServiceException(e);
        }
    }
}
