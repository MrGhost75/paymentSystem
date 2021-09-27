package controller.filter;

import controller.command.utils.CommandUtil;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class LanguageFilter implements Filter {

    private static final Logger logger = Logger.getLogger(LanguageFilter.class);

    private static final String LANGUAGE = "language";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException {
        logger.info("Start execution LanguageFilter");
        final HttpServletRequest req = (HttpServletRequest) servletRequest;
        final HttpServletResponse resp = (HttpServletResponse) servletResponse;

        //req.setCharacterEncoding("UTF-8");
        //resp.setContentType("text/html; charset=UTF-8");
        //resp.setCharacterEncoding("UTF-8");

        String path = req.getRequestURI();
        path = path.replace("language/", "");
        if (path.equals("/view//")) {
            path = "/";
        }

        logger.info("uri" + req.getRequestURI());
        logger.info("path" + path);

        String language = req.getParameter(LANGUAGE);
        boolean isEnglish = language.equals("EN");
        boolean isRussian = language.equals("RU");

        if (isEnglish) {
            req.getSession().setAttribute(LANGUAGE, "en-EN");
        } else if (isRussian) {
            req.getSession().setAttribute(LANGUAGE, "ru-RU");
        }
        logger.info("Go to "+ path +" page");
        CommandUtil.goToPage(req, resp, path);
    }

    @Override
    public void destroy() {

    }

}