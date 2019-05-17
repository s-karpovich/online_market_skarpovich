package by.tut.mdcatalog.project2.repository.impl;

import by.tut.mdcatalog.project2.repository.GenericRepository;
import by.tut.mdcatalog.project2.repository.exception.DataBaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static by.tut.mdcatalog.project2.repository.constant.RepositoryErrors.DATABASE_CONNECTION_ERROR;

public class GenericRepositoryImpl implements GenericRepository {
    private final static Logger logger = LoggerFactory.getLogger(GenericRepositoryImpl.class);
    @Autowired
    private DataSource dataSource;

    @Override
    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DataBaseException(DATABASE_CONNECTION_ERROR, e);
        }
    }
}