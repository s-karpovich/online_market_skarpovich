package by.tut.mdcatalog.project2.web.security.config;

import by.tut.mdcatalog.project2.web.security.handler.AppUrlAuthenticationSuccessHandler;
import by.tut.mdcatalog.project2.web.security.handler.WebAccessDeniedHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import static by.tut.mdcatalog.project2.web.constant.AuthorizationConstants.ADMIN_ROLE_NAME;
import static by.tut.mdcatalog.project2.web.constant.AuthorizationConstants.CUSTOMER_ROLE_NAME;
import static by.tut.mdcatalog.project2.web.constant.AuthorizationConstants.SALE_ROLE_NAME;

@Configuration
@Order(2)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder encoder;

    public WebSecurityConfig(UserDetailsService userDetailsService,
                             PasswordEncoder encoder) {
        this.userDetailsService = userDetailsService;
        this.encoder = encoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers( "/users/**", "/reviews/**")
                .hasAuthority(ADMIN_ROLE_NAME)
                .antMatchers("/articles/{\\d+}")
                .hasAnyAuthority(CUSTOMER_ROLE_NAME, SALE_ROLE_NAME)
                .antMatchers("/profile/**", "/orders/**")
                .hasAuthority(CUSTOMER_ROLE_NAME)
                .antMatchers("/items/**", "/articles/add", "/articles/comments")
                .hasAuthority(SALE_ROLE_NAME)
                .antMatchers("/", "/login", "/about", "/messages/**")
                .permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler(authenticationSuccessHandler())
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(loginAccessDeniedHandler())
                .and()
                .csrf()
                .disable();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AppUrlAuthenticationSuccessHandler();
    }

    @Bean
    public AccessDeniedHandler loginAccessDeniedHandler() {
        return new WebAccessDeniedHandler();
    }
}
