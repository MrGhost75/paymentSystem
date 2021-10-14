package controller.command;

import controller.command.utils.CommandUtil;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ClientMenuCommand implements Command {
    private static final Logger logger = Logger.getLogger(ClientMenuCommand.class);


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("ClientMenuCommand start");

        //place for some logic

        logger.info("go to mainPageUser");
        CommandUtil.goToPage(req, resp, "/WEB-INF/view/client/mainPageUser.jsp");

    }
}
