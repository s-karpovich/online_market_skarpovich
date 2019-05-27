package by.tut.mdcatalog.project2.repository.impl;

import by.tut.mdcatalog.project2.repository.model.Role;
import by.tut.mdcatalog.project2.repository.RoleRepository;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class RoleRepositoryImpl extends GenericRepositoryImpl implements RoleRepository {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public List<Role> getAll() {
        String hql = "from Role as r";
        Query query = entityManager.createQuery(hql);
        return query.getResultList();
    }

    @Override
    public Role getById(Long id) {
        String hql = "from Role as r where r.id=:id";
        Query query = entityManager.createQuery(hql);
        query.setParameter("id", id);
        return (Role) query.getSingleResult();
    }
}
