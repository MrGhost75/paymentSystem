package model.dao.factory;


import model.dao.CreditCardDao;
import model.dao.PaymentDao;
import model.dao.UserDao;
import model.dao.impl.CreditCardDaoImpl;
import model.dao.impl.PaymentDaoImpl;
import model.dao.impl.UserDaoImpl;
import org.apache.log4j.Logger;

public class DatabaseDaoFactory extends DaoFactory {

    private static final Logger logger = Logger.getLogger(DatabaseDaoFactory.class);

    private final UserDao userDao = new UserDaoImpl();
    private final CreditCardDao creditCardDao = new CreditCardDaoImpl();
    private final PaymentDao paymentDao = new PaymentDaoImpl();

    @Override
    public UserDao getUserDAO() {
        logger.info("Get UserDao");
        return userDao;
    }

    @Override
    public CreditCardDao getCreditCardDAO() {
        logger.info("Get CreditCardDao");
        return creditCardDao;
    }

    @Override
    public PaymentDao getPaymentDAO() {
        logger.info("Get PaymentDao");
        return paymentDao;
    }

}
