package by.tut.mdcatalog.project2.repository.config;


import liquibase.integration.spring.SpringLiquibase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class LiquiBaseConfig {

    @Bean
    public SpringLiquibase liquibase(DataSource source) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:migrations/db-changelog.xml");
        liquibase.setDataSource(source);
        return liquibase;
    }
}