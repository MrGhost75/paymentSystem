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

public class AddCreditCardCommand implements Command {
    private static final Logger logger = Logger.getLogger(AddCreditCardCommand.class);
    private static final String DEFAULT_PAGE = "/WEB-INF/view/addingCreditCard.jsp";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("AddCreditCardCommand start");

        User user = (User) req.getSession().getAttribute("user");
        if (user.getActivityStatus().equals("not_active")) {
            logger.warn("blocked user " + user.getName() + " tried to get to inaccessible page addingCreditCard.jsp");
            throw new NotFoundOperationException();
        }

        try {

            ServiceFactory factory = ServiceFactory.getInstance();
            UserService userService = factory.getUserService();
            CreditCardService cardService = factory.getCreditCardService();

            String cardName = req.getParameter("cardName");
            String pincode = req.getParameter("pincode");

            if (Objects.nonNull(cardName) && Objects.nonNull(pincode)) {

                if (!DataValidation.isPincodeValid(pincode)) {
                    logger.warn("invalid payment amount or receiver id");
                    throw new InvalidDataException();
                }

                if (cardService.checkIfNameExists(cardName)) {
                    logger.warn("User with such name or email already exists");
                    throw new AlreadyExistCardException();
                }

                CreditCard newCard = new CreditCard.CreditCardBuilderImpl()
                        .setName(cardName)
                        .setPincode(CommandUtil.encrypt(pincode, false).orElseThrow(PasswordGenerationException::new))
                        .setBalance(0)
                        .setActivityStatus("active")
                        .build();
                cardService.add(newCard);

                userService.addCardToUser(user.getId(), cardService.getCardByName(cardName).getId());

                String page = CommandUtil.getUserProfileByRole(user.getRole());
                logger.info("redirection to " + page);
                resp.sendRedirect(page);
            } else {
                logger.info("go to addingCreditCard");
                CommandUtil.goToPage(req, resp, "/WEB-INF/view/addingCreditCard.jsp");
            }
        } catch (InvalidDataException e) {
            req.setAttribute("invalidPin", true);
            CommandUtil.goToPage(req, resp, DEFAULT_PAGE);
        } catch (AlreadyExistCardException e) {
            req.setAttribute("existCard", true);
            CommandUtil.goToPage(req, resp, DEFAULT_PAGE);
        } catch (NamingException | DataBaseException e) {
            throw new RuntimeException(e);
        }
    }

}