package by.tut.mdcatalog.project2.repository.connection.impl;

import by.tut.mdcatalog.project2.repository.connection.ConnectionService;
import by.tut.mdcatalog.project2.repository.exception.DataBaseException;
import by.tut.mdcatalog.project2.repository.properties.DatabaseProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import static by.tut.mdcatalog.project2.repository.constant.RepositoryErrors.DATABASE_INITIALIZATION_ERROR;
import static by.tut.mdcatalog.project2.repository.constant.RepositoryErrors.DATABASE_CONNECTION_ERROR;

@Component
public class ConnectionServiceImpl implements ConnectionService {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionServiceImpl.class);
    private final DatabaseProperties databaseProperties;

    @Autowired
    public ConnectionServiceImpl(DatabaseProperties databaseProperties) {
        try {
            Class.forName(databaseProperties.getDriver());
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new DataBaseException(DATABASE_CONNECTION_ERROR, e);
        }
        this.databaseProperties = databaseProperties;
    }

    public Connection getConnection() {
        try {
            Properties properties = new Properties();
            properties.setProperty("user", databaseProperties.getUsername());
            properties.setProperty("password", databaseProperties.getPassword());
            return DriverManager.getConnection(databaseProperties.getUrl(), properties);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DataBaseException(DATABASE_CONNECTION_ERROR, e);
        }
    }

    @PostConstruct
    public void createDatabaseTables() {
        File initFile = new File(getClass().getResource("/" + databaseProperties.getInitFile()).getPath());
        String[] initQueries = getQueries(initFile);
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try (Statement statement = connection.createStatement()) {
                for (String initQuery : initQueries) {
                    statement.addBatch(initQuery);
                }
                statement.executeBatch();
                connection.commit();
            } catch (Exception e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new DataBaseException(DATABASE_INITIALIZATION_ERROR, e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DataBaseException(DATABASE_INITIALIZATION_ERROR, e);
        }
    }

    private String[] getQueries(File initFile) {
        StringBuilder allQueries = new StringBuilder();
        try {
            Files.lines(initFile.toPath()).forEach(allQueries::append);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new DataBaseException(DATABASE_INITIALIZATION_ERROR, e);
        }
        return allQueries.toString().split(";");
    }
}
