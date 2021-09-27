package controller.command;

import controller.command.utils.CommandUtil;
import controller.command.utils.DataValidation;
import model.entity.User;
import model.exception.*;
import org.apache.log4j.Logger;
import service.IUserService;
import service.factory.ServiceFactory;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
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

        try {
            if (Objects.isNull(name) || Objects.isNull(email) || Objects.isNull(password)) {
                logger.info("some fields are empty");
                throw new WrongDataException();
            }
            if (!DataValidation.isEmailValid(email)) {
                logger.info("email is not valid");
                throw new WrongDataException();
            }
            if (!DataValidation.isPasswordValid(password)) {
                logger.info("password is not valid");
                throw new WrongDataException();
            }

            IUserService userService = factory.getUserService();
            try {
                if (Objects.nonNull(userService.getUserByName(name)) ||
                        Objects.nonNull(userService.getUserByEmail(email))) {
                    logger.info("user with such name or email already exists");
                    throw new AlreadyExistUserException();
                }
            } catch (NotFoundUserException e) {

                User user = new User.UserBuilderImpl()
                        .setName(name)
                        .setEmail(email)
                        .setPassword(CommandUtil.encrypt(password, false).orElseThrow(PasswordGenerationException::new))
                        .setRole("user")
                        .setActivityStatus("active")
                        .build();

                userService.add(user);

                logger.info("successful registration, redirect to login page");
                response.sendRedirect("/");
            }
        } catch (ServiceException e) {
            request.setAttribute("notFound", true);
            CommandUtil.goToPage(request, response, DEFAULT_PAGE);
        } catch (WrongDataException e) {
            request.setAttribute("wrongData", false);
            CommandUtil.goToPage(request, response, DEFAULT_PAGE);
        } catch (AlreadyExistUserException e) {
            request.setAttribute("existUser", true);
            CommandUtil.goToPage(request, response, DEFAULT_PAGE);
        } catch (PasswordGenerationException | NamingException | SQLException e) {
            e.printStackTrace();
        }
    }
}
