package by.tut.mdcatalog.project2.repository.impl;

import by.tut.mdcatalog.project2.repository.ContactRepository;
import by.tut.mdcatalog.project2.repository.model.Contact;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class ContactRepositoryImpl extends GenericRepositoryImpl implements ContactRepository {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public void merge(Contact contact) {
        entityManager.merge(contact);
    }

    @Override
    public Contact getByUserId(Long id) {
        String hql = "from Contact as C where C.user.id=:id";
        Query query = entityManager.createQuery(hql);
        query.setParameter("id", id);
        return (Contact) query.getSingleResult();
    }
}
