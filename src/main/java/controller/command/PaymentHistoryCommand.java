package controller.command;

import controller.command.utils.CommandUtil;
import controller.command.utils.DataValidation;
import model.entity.CreditCard;
import model.entity.Payment;
import model.entity.User;
import model.exception.DataBaseException;
import model.exception.InvalidDataException;
import model.exception.WrongDataException;
import org.apache.log4j.Logger;
import service.CreditCardService;
import service.UserService;
import service.factory.ServiceFactory;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class PaymentHistoryCommand implements Command {
    private static final Logger logger = Logger.getLogger(PaymentHistoryCommand.class);
    private static final String DEFAULT_PAGE = "/WEB-INF/view/paymentHistory.jsp";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("PaymentHistoryCommand start");

        ServiceFactory factory = ServiceFactory.getInstance();
        UserService userService = factory.getUserService();

        User user = (User) req.getSession().getAttribute("user");


        String beginDateStr = req.getParameter("beginDate");
        String endDateStr = req.getParameter("endDate");
        List<Payment> payments = null;
        try {

            payments = userService.getAllUserPayments(user);

            if (Objects.nonNull(beginDateStr) && Objects.nonNull(endDateStr)) {

                if (!DataValidation.isDateValid(beginDateStr) || !DataValidation.isDateValid(endDateStr)) {
                    logger.warn("invalid date");
                    throw new InvalidDataException();
                }

                DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                long beginTime = sdf.parse(beginDateStr).getTime();
                long endTime = sdf.parse(endDateStr).getTime();

                if (beginTime >= endTime) {
                    logger.warn("end date is earlier than begin date");
                    throw new WrongDataException();
                }

                payments = userService.getAllPaymentsFromBeginToEndTime(user, beginTime, endTime);

            }
            req.setAttribute("payments", payments);
            logger.info("go to paymentHistory");
            CommandUtil.goToPage(req, resp, "/WEB-INF/view/paymentHistory.jsp");
        } catch (InvalidDataException e) {
            req.setAttribute("payments", payments);
            req.setAttribute("invalidDate", true);
            CommandUtil.goToPage(req, resp, DEFAULT_PAGE);
        } catch (WrongDataException e) {
            req.setAttribute("payments", payments);
            req.setAttribute("wrongDate", true);
            CommandUtil.goToPage(req, resp, DEFAULT_PAGE);
        } catch (NamingException | DataBaseException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}