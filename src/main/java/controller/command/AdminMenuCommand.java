package controller.command;

import controller.command.utils.CommandUtil;
import model.entity.User;
import model.exception.DataBaseException;
import org.apache.log4j.Logger;
import service.UserService;
import service.factory.ServiceFactory;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;


public class AdminMenuCommand  implements Command {
    private static final Logger logger = Logger.getLogger(AdminMenuCommand.class);


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("AdminMenuCommand start");

        ServiceFactory factory = ServiceFactory.getInstance();

        try {
            UserService userService = factory.getUserService();

            String command = request.getParameter("command");
            String userId = request.getParameter("Uid");
            if (Objects.nonNull(command)) {
                Long id = Long.parseLong(userId);
                User user = userService.getById(id);
                switch (command) {
                    case "delete":
                        logger.info("delete command execute");
                        userService.deleteEntity(id);
                        break;
                    case "unblock":
                        logger.info("unblock command execute");
                        user.setActivityStatus("active");
                        userService.updateEntity(user);
                        break;
                    case "block":
                        logger.info("block command execute");
                        user.setActivityStatus("not_active");
                        userService.updateEntity(user);
                        break;
                }
            }

            List<User> users = userService.getAll();
            request.setAttribute("users", users);
        } catch (NamingException | DataBaseException e) {
            throw new RuntimeException(e);
        }

        logger.info("go to mainPageAdmin");
        CommandUtil.goToPage(request, response, "/WEB-INF/view/admin/mainPageAdmin.jsp");

    }
}
