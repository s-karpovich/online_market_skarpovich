package by.tut.mdcatalog.project2.web.security.config;

import by.tut.mdcatalog.project2.web.security.handler.ApiAccessDeniedHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import static by.tut.mdcatalog.project2.web.constant.AuthorizationConstants.REST_API_ROLE_NAME;

@Configuration
@Order(1)
public class ApiSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    public ApiSecurityConfig(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/api/**")
                .authorizeRequests()
                .anyRequest()
                .hasAuthority(REST_API_ROLE_NAME)
                .and()
                .httpBasic()
                .and()
                .exceptionHandling().accessDeniedHandler(apiAccessDeniedHandler())
                .and()
                .csrf()
                .disable();
    }

    @Bean
    public AccessDeniedHandler apiAccessDeniedHandler() {
        return new ApiAccessDeniedHandler();
    }
}
