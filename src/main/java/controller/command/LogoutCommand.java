package controller.command;

import controller.command.utils.CommandUtil;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutCommand implements Command {
    private static final Logger logger = Logger.getLogger(LogoutCommand.class);


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("Start execution logout");

        request.getSession().invalidate();

        logger.info("go to page login");

        CommandUtil.goToPage(request, response, "/login.jsp");
    }
}
