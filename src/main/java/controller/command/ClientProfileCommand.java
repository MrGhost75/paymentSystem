package controller.command;

import controller.command.utils.CommandUtil;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ClientProfileCommand implements Command {
    private static final Logger logger = Logger.getLogger(ClientProfileCommand.class);


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("ClientProfileCommand start");

        //place for some logic

        logger.info("go to profileUser");
        CommandUtil.goToPage(req, resp, "/WEB-INF/view/client/profileUser.jsp");
    }

}