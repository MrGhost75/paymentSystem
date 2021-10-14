package controller.command;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PaymentMakingCommand  implements Command {
    private static final Logger logger = Logger.getLogger(PaymentMakingCommand.class);


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    }

}