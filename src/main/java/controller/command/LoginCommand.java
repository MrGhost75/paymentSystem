package controller.command;

import controller.command.utils.CommandUtil;
import model.entity.User;
import model.exception.DataBaseException;
import model.exception.NotFoundUserException;
import org.apache.log4j.Logger;
import service.UserService;
import service.factory.ServiceFactory;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class LoginCommand implements Command {
    private static final Logger logger = Logger.getLogger(LoginCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("Start execution login");
        ServiceFactory factory = ServiceFactory.getInstance();

        String login = req.getParameter("login");
        String password = req.getParameter("password");
        if (Objects.nonNull(login) && Objects.nonNull(password)) {

            UserService userService = factory.getUserService();

            try {
                User user = userService.getUserByName(login);

                if (Objects.nonNull(user) && CommandUtil.verifyPass(password, user.getPassword())) {
                    req.getSession().setAttribute("user", user);
                    req.getSession().setAttribute("login1", login);
                    req.getSession().setAttribute("globalUserId", user.getId());
                } else {
                    throw new NotFoundUserException();
                }

                String page = CommandUtil.getUserPageByRole(user.getRole());

                logger.info("redirection to " + page);

                resp.sendRedirect(page);

            } catch (NotFoundUserException e) {
                req.setAttribute("notFound", true);
                CommandUtil.goToPage(req, resp, "/");
            } catch (NamingException | DataBaseException throwable) {
                throw new RuntimeException(throwable);
            }
        }
    }
}
