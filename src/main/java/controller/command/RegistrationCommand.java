package controller.command;

import controller.command.utils.CommandUtil;
import controller.command.utils.DataValidation;
import model.entity.User;
import model.exception.*;
import org.apache.log4j.Logger;
import service.UserService;
import service.factory.ServiceFactory;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class RegistrationCommand implements Command {
    private static final Logger logger = Logger.getLogger(RegistrationCommand.class);

    private static final String DEFAULT_PAGE = "/WEB-INF/view/registration.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        logger.info("Start execution RegistrationCommand");

        ServiceFactory factory = ServiceFactory.getInstance();

        String name = request.getParameter("login");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (Objects.isNull(name) || Objects.isNull(email) || Objects.isNull(password)) {
            logger.warn("Some fields are empty");
            CommandUtil.goToPage(request, response, DEFAULT_PAGE);
        }

        try {
            if (!DataValidation.isEmailValid(email) || !DataValidation.isPasswordValid(password)) {
                logger.warn("Email is not valid");
                throw new InvalidDataException();
            }

            UserService userService = factory.getUserService();

            if (userService.checkIfNameExists(name) ||
                    userService.checkIfEmailExists(email)) {
                logger.warn("User with such name or email already exists");
                throw new AlreadyExistUserException();
            }

            User user = new User.UserBuilderImpl()
                    .setName(name)
                    .setEmail(email)
                    .setPassword(CommandUtil.encrypt(password, false).orElseThrow(PasswordGenerationException::new))
                    .setRole("user")
                    .setActivityStatus("active")
                    .build();
            userService.add(user);
            logger.info("Successful registration, redirect to login page");
            response.sendRedirect("/");
        } catch (InvalidDataException e) {
            request.setAttribute("invalidData", true);
            CommandUtil.goToPage(request, response, DEFAULT_PAGE);
        } catch (AlreadyExistUserException e) {
            request.setAttribute("existUser", true);
            CommandUtil.goToPage(request, response, DEFAULT_PAGE);
        } catch (NamingException | DataBaseException e) {
            throw new RuntimeException(e);
        }
    }
}
