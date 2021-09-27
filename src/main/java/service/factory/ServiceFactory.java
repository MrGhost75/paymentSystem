package service.factory;

import org.apache.log4j.Logger;
import service.ICreditCardService;
import service.IPaymentService;
import service.IUserService;
import service.impl.CreditCardService;
import service.impl.PaymentService;
import service.impl.UserService;



public class ServiceFactory {
    private static final Logger logger = Logger.getLogger(ServiceFactory.class);

    private final IUserService userService = new UserService();
    private final ICreditCardService creditCardService = new CreditCardService();
    private final IPaymentService paymentService = new PaymentService();

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

    public IUserService getUserService() {
        logger.info("Get UserService");
        return userService;
    }

    public ICreditCardService getCreditCardService() {
        logger.info("Get CreditCardService");
        return creditCardService;
    }

    public IPaymentService getPaymentService() {
        logger.info("Get PaymentService");
        return paymentService;
    }

}
