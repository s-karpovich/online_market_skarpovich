package by.tut.mdcatalog.project2.repository.impl;

import by.tut.mdcatalog.project2.repository.model.User;
import by.tut.mdcatalog.project2.repository.UserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserRepositoryImpl extends GenericRepositoryImpl<Long, User> implements UserRepository {

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
    public User getByUsername(String username) {
        String hql = "from User as U where U.username=:username";
        Query query = entityManager.createQuery(hql);
        query.setParameter("username", username);
        try {
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }


    @Override
    public List<User> getAllWithOrder() {
        String hql = "from User as U where deleted=false ORDER BY username ASC";
        Query query = entityManager.createQuery(hql);
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
