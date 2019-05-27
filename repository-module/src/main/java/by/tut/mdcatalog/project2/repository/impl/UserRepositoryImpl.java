package by.tut.mdcatalog.project2.repository.impl;

import by.tut.mdcatalog.project2.repository.model.User;
import by.tut.mdcatalog.project2.repository.UserRepository;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserRepositoryImpl extends GenericRepositoryImpl implements UserRepository {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public void persist(User user) { entityManager.persist(user); }

    @Override
    public void merge(User user) { entityManager.merge(user); }

    @Override
    public void delete(User user) {
        entityManager.remove(user);
    }

    @Override
    public void updateUserRole(Long roleId, Long userId) {
        String hql = "update User U set U.role.id=:roleId where U.id=:id";
        Query query = entityManager.createQuery(hql);
        query.setParameter("roleId", roleId);
        query.setParameter("id", userId);
        query.executeUpdate();
    }

    @Override
    public void resetPassword(String encodedPassword, Long id) {
        String hql = "update User U set U.password=:password WHERE U.id =:id";
        Query query = entityManager.createQuery(hql);
        query.setParameter("password", encodedPassword);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public List<User> getAll() {
        String hql = "from User as U";
        Query query = entityManager.createQuery(hql);
        return query.getResultList();
    }

    @Override
    public User getById(Long id) {
        String hql = "from User as U where U.id=:id";
        Query query = entityManager.createQuery(hql);
        query.setParameter("id", id);
        return (User) query.getSingleResult();
    }

    @Override
    public User getByUsername(String username) {
        String hql = "from User as U where U.username=:username";
        Query query = entityManager.createQuery(hql);
        query.setParameter("username", username);
        return (User) query.getSingleResult();
    }
}
