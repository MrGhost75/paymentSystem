package controller.filter;

import controller.command.utils.CommandUtil;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthorizationFilter implements Filter {
    private static final Logger logger = Logger.getLogger(AuthorizationFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("Start execution AuthorizationFilter");

        final HttpServletRequest req = (HttpServletRequest) servletRequest;
        final HttpServletResponse resp = (HttpServletResponse) servletResponse;
        final HttpSession session = req.getSession(false);
        String loginURI = req.getContextPath() + "/view/login";
        String registrationURI = req.getContextPath() + "/view/registration";

        boolean loggedIn = session != null && session.getAttribute("user") != null;
        boolean loginRequest = req.getRequestURI().equals(loginURI);
        boolean signUpRequest = req.getRequestURI().equals(registrationURI);

        if (loggedIn || loginRequest || signUpRequest) {
            filterChain.doFilter(req, resp);

        } else if (req.getRequestURI().equals("/view/registration")) {
            logger.info("Go to registration page");
            CommandUtil.goToPage(req, resp, "/WEB-INF/view/registration.jsp");
            //req.getRequestDispatcher("/WEB-INF/view/registration.jsp").forward(req, resp);
        } else {
            logger.info("Go to login page");
            CommandUtil.goToPage(req, resp, "/");
            // req.getRequestDispatcher("/").forward(req, resp);
        }

    }

    @Override
    public void destroy() {

    }

}
