package controller.command;

import controller.command.utils.CommandUtil;
import controller.command.utils.DataValidation;
import model.entity.CreditCard;
import model.entity.User;
import model.exception.*;
import org.apache.log4j.Logger;
import service.CreditCardService;
import service.UserService;
import service.factory.ServiceFactory;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class BalanceReplenishmentCommand implements Command {
    private static final Logger logger = Logger.getLogger(PaymentMakingCommand.class);
    private static final String DEFAULT_PAGE = "/WEB-INF/view/balanceReplenishment.jsp";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("PaymentMakingCommand start");

        User currentUser = (User) req.getSession().getAttribute("user");
        if (currentUser.getActivityStatus().equals("not_active")) {
            logger.warn("blocked user " + currentUser.getName() + " tried to get to inaccessible page balanceReplenishment.jsp");
            throw new NotFoundOperationException();
        }

        try {

            ServiceFactory factory = ServiceFactory.getInstance();
            UserService userService = factory.getUserService();
            CreditCardService cardService = factory.getCreditCardService();

            CreditCard pickedCard = null;

            String command = req.getParameter("command");
            String cardIdStr = req.getParameter("Cid");
            if (Objects.nonNull(command)) {
                long id = Long.parseLong(cardIdStr);
                pickedCard = cardService.getById(id);
                switch (command) {
                    case "pick":
                        logger.info("pick command execute");
                        req.getSession().setAttribute("pickedCard", pickedCard);
                        break;
                    case "unpick":
                        logger.info("unpick command execute");
                        pickedCard = null;
                        req.getSession().setAttribute("pickedCard", null);
                        break;
                }
            }

            List<CreditCard> creditCards = userService.getAllUserActiveCards(currentUser);
            req.setAttribute("creditCards", creditCards);

            if (Objects.nonNull(pickedCard)) {
                String pincode = req.getParameter("pincode");
                String paymentAmountStr = req.getParameter("paymentAmount");

                if (Objects.isNull(pincode) || Objects.isNull(paymentAmountStr)) {
                    logger.warn("Some fields are empty");
                    CommandUtil.goToPage(req, resp, DEFAULT_PAGE);
                } else {

                    if (!CommandUtil.verifyPass(pincode, pickedCard.getPincode())) {
                        logger.warn("wrong pin-code for picked card");
                        throw new WrongDataException();
                    }

                    if (!DataValidation.isNumber(paymentAmountStr)) {
                        logger.warn("invalid payment amount or receiver id");
                        throw new InvalidDataException();
                    }

                    long paymentAmount = Long.parseLong(paymentAmountStr);

                    if (paymentAmount < 0) {
                        logger.warn("negative amount of money");
                        throw new NegativeAmountOfMoneyException();
                    }

                    pickedCard.setBalance(pickedCard.getBalance() + paymentAmount);
                    cardService.updateEntity(pickedCard);

                    String page = CommandUtil.getUserProfileByRole(currentUser.getRole());
                    logger.info("redirection to " + page);
                    resp.sendRedirect(page);
                }
            } else {
                logger.info("go to balanceReplenishment");
                CommandUtil.goToPage(req, resp, DEFAULT_PAGE);
            }
        } catch (WrongDataException e) {
            req.setAttribute("wrongPin", true);
            CommandUtil.goToPage(req, resp, DEFAULT_PAGE);
        } catch (InvalidDataException e) {
            req.setAttribute("invalidData", true);
            CommandUtil.goToPage(req, resp, DEFAULT_PAGE);
        } catch (NegativeAmountOfMoneyException e) {
            req.setAttribute("negativeAmount", true);
            CommandUtil.goToPage(req, resp, DEFAULT_PAGE);
        } catch (NamingException | DataBaseException e) {
            throw new RuntimeException(e);
        }
    }
}