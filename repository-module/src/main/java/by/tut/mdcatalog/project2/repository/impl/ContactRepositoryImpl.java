package by.tut.mdcatalog.project2.repository.impl;

import by.tut.mdcatalog.project2.repository.ContactRepository;
import by.tut.mdcatalog.project2.repository.constant.RepositoryErrors;
import by.tut.mdcatalog.project2.repository.exception.DataBaseException;
import by.tut.mdcatalog.project2.repository.model.Contact;
import by.tut.mdcatalog.project2.repository.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class ContactRepositoryImpl extends GenericRepositoryImpl implements ContactRepository {

    private static final Logger logger = LoggerFactory.getLogger(ContactRepositoryImpl.class);

    @Override
    public Contact getByUserId(Connection connection, Long id) {
        String sqlQuery = "SELECT * FROM contact WHERE user_id=? AND deleted=false";  // Get all excepting deleted
        Contact contact = new Contact();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    contact = buildContact(resultSet);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DataBaseException(String.format(RepositoryErrors.DATABASE_QUERY_ERROR, sqlQuery), e);
        }
        return contact;
    }

    private Contact buildContact(ResultSet resultSet) throws SQLException {
        Contact contact = new Contact();
        contact.setId(resultSet.getLong("id"));
        contact.setPhone(resultSet.getString("phone"));
        contact.setAddress(resultSet.getString("address"));
        User user = new User();
        user.setId(resultSet.getLong("user_id"));
        contact.setUser(user);
        contact.setDeleted(resultSet.getBoolean("deleted"));
        return contact;
    }
}
