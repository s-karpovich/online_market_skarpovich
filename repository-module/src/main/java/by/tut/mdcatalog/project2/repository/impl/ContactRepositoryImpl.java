package by.tut.mdcatalog.project2.repository.impl;

import by.tut.mdcatalog.project2.repository.ContactRepository;
import by.tut.mdcatalog.project2.repository.model.Contact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class ContactRepositoryImpl extends GenericRepositoryImpl implements ContactRepository {

    private static final Logger logger = LoggerFactory.getLogger(ContactRepositoryImpl.class);

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
//
//    @Override
//    public void update(Contact contact) {
//        String hql = "update Contact C set C.phone=:phone, C.address=:address WHERE C.id =:id";
//        Query query = entityManager.createQuery(hql);
//        query.setParameter("phone", contact.getPhone());
//        query.setParameter("address", contact.getAddress());
//        query.setParameter("id", contact.getId());
//        query.executeUpdate();
//    }
}
