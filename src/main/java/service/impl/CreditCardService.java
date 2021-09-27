package service.impl;

import model.dao.CreditCardDao;
import model.dao.factory.DaoFactory;
import model.entity.CreditCard;
import model.exception.DataBaseException;
import model.exception.ServiceException;

import org.apache.log4j.Logger;
import service.ICreditCardService;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.List;

public class CreditCardService implements ICreditCardService {

    private static final Logger logger = Logger.getLogger(PaymentService.class);

    private final DaoFactory daoFactory = DaoFactory.getInstance();
    private final CreditCardDao creditCardDao = daoFactory.getCreditCardDAO();


    @Override
    public List<CreditCard> getAll() throws NamingException {
        try {
            return creditCardDao.getAll();
        } catch (DataBaseException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public CreditCard getById(Long id) throws SQLException, NamingException {
        try {
            return creditCardDao.getById(id);
        } catch (DataBaseException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean add(CreditCard entity) throws SQLException, NamingException {
        try {
            return creditCardDao.add(entity);
        } catch (DataBaseException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateEntity(CreditCard entity) throws SQLException, NamingException {
        try {
            return creditCardDao.updateEntity(entity);
        } catch (DataBaseException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean deleteEntity(Long id) throws SQLException, NamingException {
        try {
            return creditCardDao.deleteEntity(id);
        } catch (DataBaseException e) {
            throw new ServiceException(e);
        }
    }

}
