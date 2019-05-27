package by.tut.mdcatalog.project2.repository.impl;

import by.tut.mdcatalog.project2.repository.ItemRepository;
import by.tut.mdcatalog.project2.repository.model.Item;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ItemRepositoryImpl extends GenericRepositoryImpl implements ItemRepository {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public void persist(Item item) { entityManager.persist(item); }

    @Override
    public void delete(Item item) {
        entityManager.remove(item);
    }

    @Override
    public List<Item> getAll() {
        String hql = "from Item as I";
        Query query = entityManager.createQuery(hql);
        return query.getResultList();
}

    @Override
    public Item getById (Long id) {
        String hql = "from Item as I where I.id=:id";
        Query query = entityManager.createQuery(hql);
        query.setParameter("id", id);
        return (Item) query.getSingleResult();
    }
}
