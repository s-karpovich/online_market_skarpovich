package by.tut.mdcatalog.project2.repository;

import by.tut.mdcatalog.project2.repository.model.User;

import java.util.List;

public interface UserRepository extends GenericRepository<Long, User>{

    List<User> getAllWithOrder();

    User getByUsername(String username);

    void updateUserRole(Long roleId, Long userId);

    void resetPassword(String encodedPassword, Long id);
}
