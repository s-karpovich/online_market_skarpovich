package by.tut.mdcatalog.project2.repository.impl;

import by.tut.mdcatalog.project2.repository.constant.RepositoryErrors;
import by.tut.mdcatalog.project2.repository.exception.DataBaseException;
import by.tut.mdcatalog.project2.repository.model.Role;
import by.tut.mdcatalog.project2.repository.model.User;
import by.tut.mdcatalog.project2.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);

    @Override
    public List<User> getUsers(Connection connection) {
        String sqlQuery = "SELECT * FROM user WHERE deleted=false";
        List<User> userList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    userList.add(buildUser(resultSet));
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DataBaseException(String.format(RepositoryErrors.DATABASE_QUERY_ERROR, sqlQuery), e);
        }
        return userList;
    }

    @Override
    public User getById(Connection connection, Long id) {
        String sqlQuery = "SELECT * FROM user WHERE id=? AND deleted=false;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return buildUser(resultSet);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DataBaseException(String.format(RepositoryErrors.DATABASE_QUERY_ERROR, sqlQuery), e);
        }
        return null;
    }

    @Override
    public User getByUsername(Connection connection, String username) {
        String sqlQuery = "SELECT * FROM user WHERE username=? AND deleted=false;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return buildUser(resultSet);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DataBaseException(String.format(RepositoryErrors.DATABASE_QUERY_ERROR, sqlQuery), e);
        }
        return null;
    }

    @Override
    public void add(Connection connection, User user) {
        String sqlQuery = "INSERT INTO user (username,password,firstname,middlename,surname,deleted,role_id) VALUES (?,?,?,?,?,?,(SELECT id FROM role WHERE name=?))";
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                sqlQuery,
                Statement.RETURN_GENERATED_KEYS
        )) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFirstname());
            preparedStatement.setString(4, user.getMiddlename());
            preparedStatement.setString(5, user.getSurname());
            preparedStatement.setBoolean(6, false);
            preparedStatement.setString(7, user.getRole().getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DataBaseException(String.format(RepositoryErrors.DATABASE_QUERY_ERROR, sqlQuery), e);
        }
    }

    @Override
    public void updateUserRole(Connection connection, Long roleId, Long userId) {
        String sqlQuery = "UPDATE user SET role_id=? WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setLong(1, roleId);
            preparedStatement.setLong(2, userId);
            preparedStatement.executeUpdate();
            logger.info("Updated successfully, updated user id:{} , new role id:{}", roleId, userId);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DataBaseException(String.format(RepositoryErrors.DATABASE_QUERY_ERROR, sqlQuery), e);
        }
    }

    @Override
    public void deleteUsers(Connection connection, int[] ids) {
        StringBuilder sqlQuery = new StringBuilder("UPDATE user SET deleted=true WHERE id IN (");
        for (int i = 0; i < ids.length; i++) {
            if (i != ids.length - 1) sqlQuery.append(ids[i]).append(',');
            else sqlQuery.append(ids[i]).append(')');
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery.toString())) {
            preparedStatement.executeUpdate();
            logger.info("Users deleted (IDs):{}", ids);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DataBaseException(String.format(RepositoryErrors.DATABASE_QUERY_ERROR, sqlQuery), e);
        }
    }

    @Override
    public void resetPassword(Connection connection, String encodedPassword, Long id) {
        String sqlQuery = "UPDATE user SET password=? WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, encodedPassword);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
            logger.info("User password updated (ID):{}", id);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DataBaseException(String.format(RepositoryErrors.DATABASE_QUERY_ERROR, sqlQuery), e);
        }
    }

    private User buildUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setFirstname(resultSet.getString("firstname"));
        user.setMiddlename(resultSet.getString("middlename"));
        user.setSurname(resultSet.getString("surname"));
        user.setDeleted(resultSet.getBoolean("deleted"));
        Role role = new Role();
        role.setId(resultSet.getLong("role_id"));
        user.setRole(role);
        return user;
    }
}
