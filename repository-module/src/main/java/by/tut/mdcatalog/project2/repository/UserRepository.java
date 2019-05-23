package by.tut.mdcatalog.project2.repository;

import by.tut.mdcatalog.project2.repository.model.User;

import java.sql.Connection;
import java.util.List;

public interface UserRepository extends GenericRepository {

    List<User> getUsers(Connection connection);

    User getById(Connection connection, Long id);

    User getByUsername(Connection connection, String username);

    void add(Connection connection, User user);

    void updateUserRole(Connection connection, Long roleId, Long userId);

    void update(Connection connection, User user);

    void deleteUsers(Connection connection, int[] ids);

    void resetPassword(Connection connection, String encodedPassword, Long id);
}
