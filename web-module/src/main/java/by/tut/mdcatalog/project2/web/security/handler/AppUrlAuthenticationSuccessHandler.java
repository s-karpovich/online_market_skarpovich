package by.tut.mdcatalog.project2.web.security.handler;

import by.tut.mdcatalog.project2.web.constant.AuthorizationConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

@Component
public class AppUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static Logger logger = LoggerFactory.getLogger(AppUrlAuthenticationSuccessHandler.class);

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        handle(request, response, authentication);

        clearAuthenticationAttributes(request);
    }

    private void handle(HttpServletRequest request,
                        HttpServletResponse response,
                        Authentication authentication) throws IOException {

        String targetUrl = targetURL(authentication);

        if (response.isCommitted()) {
            logger.info("Response commited. Redirect has not been processed" + targetUrl);
            return;
        }
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    private String targetURL(Authentication authentication) {
        boolean isCustomer = false;
        boolean isAdministrator = false;
        boolean isSale = false;
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals(AuthorizationConstants.CUSTOMER_ROLE_NAME)) {
                isCustomer = true;
                break;
            } else if (grantedAuthority.getAuthority().equals(AuthorizationConstants.ADMIN_ROLE_NAME)) {
                isAdministrator = true;
                break;
            } else if (grantedAuthority.getAuthority().equals(AuthorizationConstants.SALE_ROLE_NAME)) {
                isSale = true;
                break;
            }
        }
        if (isCustomer) {
            return "/profile";
        } else if (isAdministrator) {
            return "/users";
        } else if (isSale) {
            return "/items";
        } else {
            logger.info("Impossible to redirect. User role:" + authentication.getCredentials() + " - is not recognized.");
            return "redirect:/login?role";
        }
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}
