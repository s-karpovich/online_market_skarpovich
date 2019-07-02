package by.tut.mdcatalog.project2.web.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(
        scanBasePackages = "by.tut.mdcatalog.project2.*",
        exclude = UserDetailsServiceAutoConfiguration.class
)
@EntityScan("by.tut.mdcatalog.project2.repository.model")
public class SpringBootModuleApp {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootModuleApp.class, args);
    }
}
