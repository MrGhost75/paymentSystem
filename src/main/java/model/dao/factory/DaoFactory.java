package model.dao.factory;

import model.dao.CreditCardDao;
import model.dao.PaymentDao;
import model.dao.UserDao;

public abstract class DaoFactory {
    private static DaoFactory instance;

    public static synchronized DaoFactory getInstance() {
        if (instance == null) {
            instance = new DatabaseDaoFactory();
        }
        return instance;
    }

    public abstract UserDao getUserDAO();

    public abstract CreditCardDao getCreditCardDAO();

    public abstract PaymentDao getPaymentDAO();

}
