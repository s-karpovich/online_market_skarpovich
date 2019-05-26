package by.tut.mdcatalog.project2.repository.impl;

import by.tut.mdcatalog.project2.repository.model.Role;
import by.tut.mdcatalog.project2.repository.model.User;
import by.tut.mdcatalog.project2.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserRepositoryImpl extends GenericRepositoryImpl implements UserRepository {

    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);

    @PersistenceContext
    protected EntityManager entityManager;

    // Refactor!
//    @Override
//    public void create(User user) {
//        String hql = "INSERT into User (username,password,firstname,middlename,surname,role) select U.username=:username, U.password=:password, U.firstname=:firstname, U.middlename=:middlename, U.surname=:surname, U.role=:role from User U";
//        Query query = entityManager.createQuery(hql);
//        query.setParameter("username", user.getUsername());
//        query.setParameter("password", user.getPassword());
//        query.setParameter("firstname", user.getFirstname());
//        query.setParameter("middlename", user.getMiddlename());
//        query.setParameter("surname", user.getSurname());
//        query.setParameter("role", user.getRole().getName());
//        query.executeUpdate();
//    }

//    @Override
//    public void create(User user) {
//        String sql = "INSERT INTO user (username,password,firstname,middlename,surname,role_id) VALUES (user,?,?,?,?,(SELECT id FROM role WHERE name=?))";
//        Query query = entityManager.createNativeQuery(sql);
//        query.executeUpdate();
//    }

//    @Override
//    public void create(User user) {
//        entityManager.persist(user);
//    }

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

    //    @Override
//    public void update(User user) {
//        String hql = "update User U set U.firstname=:firstname, U.surname=:surname, U.password=:password WHERE U.id =:id";
//        Query query = entityManager.createQuery(hql);
//        query.setParameter("firstname", user.getFirstname());
//        query.setParameter("surname", user.getSurname());
//        query.setParameter("password", user.getPassword());
//        query.setParameter("id", user.getId());
//        query.executeUpdate();
//    }

    //    @Override
//    public void deleteUsers(int[] ids) {
//        StringBuilder inQuery = new StringBuilder("(");
//        for (int i = 0; i < ids.length; i++) {
//            if (i != ids.length - 1) inQuery.append(ids[i]).append(',');
//            else inQuery.append(ids[i]).append(")");
//        }
//        String hql = "update User U set deleted=true where id in" + inQuery;
//        Query query = entityManager.createQuery(hql);
//        query.executeUpdate();
//    }
}
