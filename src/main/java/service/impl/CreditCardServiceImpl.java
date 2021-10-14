package service.impl;

import model.dao.CreditCardDao;
import model.dao.constants.LogInfo;
import model.dao.factory.DaoFactory;
import model.entity.CreditCard;

import model.exception.DataBaseException;
import org.apache.log4j.Logger;
import service.CreditCardService;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.List;

public class CreditCardServiceImpl implements CreditCardService {

    private static final Logger logger = Logger.getLogger(PaymentServiceImpl.class);

    private final DaoFactory daoFactory = DaoFactory.getInstance();
    private final CreditCardDao creditCardDao = daoFactory.getCreditCardDAO();

    @Override
    public boolean add(CreditCard entity) throws NamingException {
        return creditCardDao.add(entity);
    }

    @Override
    public List<CreditCard> getAll() throws NamingException, DataBaseException {
        try {
            return creditCardDao.getAll();
        } catch (SQLException e) {
            logger.error(LogInfo.GET_ALL + LogInfo.FAILED, e.getCause());
            throw new DataBaseException(e);
        }
    }

    @Override
    public CreditCard getById(Long id) throws NamingException, DataBaseException {
        try {
            return creditCardDao.getById(id);
        } catch (SQLException e) {
            logger.error(LogInfo.GET_BY_ID + LogInfo.FAILED, e.getCause());
            throw new DataBaseException(e);
        }
    }

    @Override
    public boolean updateEntity(CreditCard entity) throws NamingException {
        return creditCardDao.updateEntity(entity);
    }

    @Override
    public boolean deleteEntity(Long id) throws NamingException {
        return creditCardDao.deleteEntity(id);
    }

}
