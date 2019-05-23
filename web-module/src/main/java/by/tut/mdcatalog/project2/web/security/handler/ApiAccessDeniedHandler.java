package by.tut.mdcatalog.project2.web.security.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ApiAccessDeniedHandler implements AccessDeniedHandler {

    private static Logger logger = LoggerFactory.getLogger(ApiAccessDeniedHandler.class);

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException e) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            logger.info("{} requested API access to restricted zone: {}", auth.getName(), request.getRequestURI());
        }
        response.sendRedirect(request.getContextPath() + "/403");
    }
}
