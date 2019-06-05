package by.tut.mdcatalog.project2.repository.impl;

import by.tut.mdcatalog.project2.repository.ItemRepository;
import by.tut.mdcatalog.project2.repository.model.Contact;
import by.tut.mdcatalog.project2.repository.model.Item;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ItemRepositoryImpl extends GenericRepositoryImpl<Long, Item> implements ItemRepository {

    @Override
    public List<Item> getAllWithOrder() {
        String hql = "FROM Item as I where deleted=false ORDER BY name DESC";
        Query query = entityManager.createQuery(hql);
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
