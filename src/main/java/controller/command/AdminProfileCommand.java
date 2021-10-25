package controller.command;

import controller.command.utils.CommandUtil;
import model.entity.CreditCard;
import model.entity.User;
import model.exception.DataBaseException;
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

public class AdminProfileCommand implements Command {
    private static final Logger logger = Logger.getLogger(AdminProfileCommand.class);


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("AdminProfileCommand start");

        ServiceFactory factory = ServiceFactory.getInstance();

        try {
            UserService userService = factory.getUserService();
            CreditCardService cardService = factory.getCreditCardService();

            User user = (User) req.getSession().getAttribute("user");
            String command = req.getParameter("command");
            String cardId = req.getParameter("Cid");
            if (Objects.nonNull(command)) {
                long id = Long.parseLong(cardId);
                CreditCard card = cardService.getById(id);
                switch (command) {
                    case "delete":
                        logger.info("delete command execute");
                        cardService.deleteEntity(id);
                        break;
                    case "unblock":
                        logger.info("unblock command execute");
                        card.setActivityStatus("active");
                        cardService.updateEntity(card);
                        break;
                    case "block":
                        logger.info("block command execute");
                        card.setActivityStatus("not_active");
                        cardService.updateEntity(card);
                        break;
                }
            }

            List<CreditCard> creditCards = userService.getAllUserCards(user);
            req.setAttribute("creditCards", creditCards);
        } catch (NamingException | DataBaseException e) {
            throw new RuntimeException(e);
        }

        logger.info("go to profileAdmin");
        CommandUtil.goToPage(req, resp, "/WEB-INF/view/admin/profileAdmin.jsp");
    }


}
