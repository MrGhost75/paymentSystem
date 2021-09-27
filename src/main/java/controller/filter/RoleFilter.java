package controller.filter;

import controller.command.utils.CommandUtil;
import model.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

public class RoleFilter implements Filter {
    private static final Logger logger = Logger.getLogger(RoleFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {


    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        logger.info("Start execution RoleFilter");

        final HttpServletRequest req = (HttpServletRequest) servletRequest;
        final HttpServletResponse resp = (HttpServletResponse) servletResponse;
        final HttpSession session = req.getSession(false);

        User user = (User) session.getAttribute("user");


        if (Objects.nonNull(user)) {
            String role = user.getRole();
            if (role.equals("admin")) {
                filterChain.doFilter(req, resp);
            } else {
                logger.info("Go to mainPageUser page");
                CommandUtil.goToPage(req, resp, "/view/client/mainPageUser");
            }
        } else {
            logger.info("Go to login page");
            CommandUtil.goToPage(req, resp, "/");
        }
    }

    @Override
    public void destroy() {

    }
}
