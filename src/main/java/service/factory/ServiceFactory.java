package service.factory;

import org.apache.log4j.Logger;
import service.CreditCardService;
import service.PaymentService;
import service.UserService;
import service.impl.CreditCardServiceImpl;
import service.impl.PaymentServiceImpl;
import service.impl.UserServiceImpl;


public class ServiceFactory {
    private static final Logger logger = Logger.getLogger(ServiceFactory.class);

    private final UserService userService = new UserServiceImpl();
    private final CreditCardService creditCardService = new CreditCardServiceImpl();
    private final PaymentService paymentService = new PaymentServiceImpl();

    private static ServiceFactory instance;

    public static ServiceFactory getInstance() {
        if (instance == null) {
            logger.info("Create ServiceFactory");
            instance = new ServiceFactory();
        }
        return instance;
    }

    private ServiceFactory() {

    }

    public UserService getUserService() {
        logger.info("Get UserServiceImpl");
        return userService;
    }

    public CreditCardService getCreditCardService() {
        logger.info("Get CreditCardServiceImpl");
        return creditCardService;
    }

    public PaymentService getPaymentService() {
        logger.info("Get PaymentServiceImpl");
        return paymentService;
    }

}
