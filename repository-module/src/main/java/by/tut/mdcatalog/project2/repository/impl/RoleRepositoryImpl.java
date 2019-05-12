package by.tut.mdcatalog.project2.repository.impl;

import by.tut.mdcatalog.project2.repository.constant.RepositoryErrors;
import by.tut.mdcatalog.project2.repository.exception.DataBaseException;
import by.tut.mdcatalog.project2.repository.model.Role;
import by.tut.mdcatalog.project2.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RoleRepositoryImpl extends GenericRepositoryImpl implements RoleRepository {

    private static final Logger logger = LoggerFactory.getLogger(RoleRepositoryImpl.class);

    @Override
    public List<Role> getRoles(Connection connection) {
        String sqlQuery = "SELECT * FROM role";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            List<Role> roles = new ArrayList<>();
            while (resultSet.next()) {
                roles.add(buildRole(resultSet));
            }
            logger.info("Roles found:{}", roles.size());
            return roles;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DataBaseException("Delete users error" + e.getMessage(), e);
        }
    }

    @Override
    public Role getById(Connection connection, Long id) {
        String sqlQuery = "SELECT * FROM role WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return buildRole(resultSet);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DataBaseException(String.format(RepositoryErrors.DATABASE_QUERY_ERROR, sqlQuery), e);
        }
        return null;
    }

    private Role buildRole(ResultSet resultSet) throws SQLException {
        Role role = new Role();
        role.setId(resultSet.getLong("id"));
        role.setName(resultSet.getString("name"));
        return role;
    }
}
