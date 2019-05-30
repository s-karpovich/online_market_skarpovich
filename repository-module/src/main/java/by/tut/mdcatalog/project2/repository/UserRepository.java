package by.tut.mdcatalog.project2.repository;

import by.tut.mdcatalog.project2.repository.model.User;

import java.util.List;

public interface UserRepository extends GenericRepository<Long, User>{

//    void persist(User user);
//
//    void merge(User user);
//
//    void delete(User user);
//
    // List<User> getAll();
//
//    User getById(Long id);

    List<User> getAllWithOrder();

    User getByUsername(String username);

    void updateUserRole(Long roleId, Long userId);

    void resetPassword(String encodedPassword, Long id);
}
