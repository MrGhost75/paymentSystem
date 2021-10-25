package controller.command;

import controller.command.utils.CommandUtil;
import controller.command.utils.DataValidation;
import model.entity.CreditCard;
import model.entity.Payment;
import model.entity.User;
import model.exception.*;
import org.apache.log4j.Logger;
import service.CreditCardService;
import service.PaymentService;
import service.UserService;
import service.factory.ServiceFactory;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class PaymentMakingCommand  implements Command {

    private static final Logger logger = Logger.getLogger(PaymentMakingCommand.class);
    private static final String DEFAULT_PAGE = "/WEB-INF/view/paymentMaking.jsp";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("PaymentMakingCommand start");

        User currentUser = (User) req.getSession().getAttribute("user");
        if (currentUser.getActivityStatus().equals("not_active")) {
            logger.warn("blocked user " + currentUser.getName() + " tried to get to inaccessible page paymentMaking.jsp");
            throw new NotFoundOperationException();
        }

        try {

            ServiceFactory factory = ServiceFactory.getInstance();
            UserService userService = factory.getUserService();
            CreditCardService cardService = factory.getCreditCardService();
            PaymentService paymentService = factory.getPaymentService();

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
                String receiverCardIdStr = req.getParameter("receiverCardId");

                if (Objects.isNull(pincode) || Objects.isNull(paymentAmountStr) || Objects.isNull(receiverCardIdStr)) {
                    logger.warn("Some fields are empty");
                    CommandUtil.goToPage(req, resp, DEFAULT_PAGE);
                } else {

                    if (!CommandUtil.verifyPass(pincode, pickedCard.getPincode())) {
                        logger.warn("wrong pin-code for picked card");
                        throw new WrongDataException();
                    }

                    if (!DataValidation.isNumber(paymentAmountStr) || !DataValidation.isNumber(receiverCardIdStr)) {
                        logger.warn("invalid payment amount or receiver id");
                        throw new InvalidDataException();
                    }

                    long paymentAmount = Long.parseLong(paymentAmountStr);
                    long receiverCardId = Long.parseLong(receiverCardIdStr);

                    if (paymentAmount > pickedCard.getBalance()) {
                        logger.warn("not enough funds");
                        throw new NotEnoughFundsException();
                    }

                    if (!cardService.checkIfIdExists(receiverCardId)
                            && !cardService.getById(receiverCardId).getActivityStatus().equals("active")) {
                        logger.warn("no receiver card with such id or it's blocked");
                        throw new NotFoundCreditCardException();
                    }

                    User receiver = userService.getUserByCardId(receiverCardId);
                    CreditCard receiverCard = cardService.getById(receiverCardId);

                    pickedCard.setBalance(pickedCard.getBalance() - paymentAmount);
                    cardService.updateEntity(pickedCard);

                    receiverCard.setBalance(receiverCard.getBalance() + paymentAmount);
                    cardService.updateEntity(receiverCard);

                    Payment newPayment = new Payment.PaymentBuilderImpl()
                            .setAmount(paymentAmount)
                            .setDate(new Date(System.currentTimeMillis()))
                            .setDescription(paymentService.formDescription(currentUser, pickedCard.getId(), receiver, receiverCardId))
                            .setPaymentStatus("sent")
                            .build();
                    paymentService.add(newPayment);

                    userService.addPaymentToUser(currentUser.getId(), paymentService.getIdOfPayment(newPayment));

                    String page = CommandUtil.getUserProfileByRole(currentUser.getRole());
                    logger.info("redirection to " + page);
                    resp.sendRedirect(page);
                }
            } else {
                logger.info("go to paymentMaking");
                CommandUtil.goToPage(req, resp, DEFAULT_PAGE);
            }
        } catch (WrongDataException e) {
            req.setAttribute("wrongPin", true);
            CommandUtil.goToPage(req, resp, DEFAULT_PAGE);
        } catch (InvalidDataException e) {
            req.setAttribute("invalidData", true);
            CommandUtil.goToPage(req, resp, DEFAULT_PAGE);
        } catch (NotEnoughFundsException e) {
            req.setAttribute("notEnoughFunds", true);
            CommandUtil.goToPage(req, resp, DEFAULT_PAGE);
        } catch (NotFoundCreditCardException e) {
            req.setAttribute("notFoundCard", true);
            CommandUtil.goToPage(req, resp, DEFAULT_PAGE);
        } catch (NamingException | DataBaseException e) {
            throw new RuntimeException(e);
        }
    }
}