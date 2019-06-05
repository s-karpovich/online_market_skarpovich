package by.tut.mdcatalog.project2.repository.impl;

import by.tut.mdcatalog.project2.repository.ContactRepository;
import by.tut.mdcatalog.project2.repository.model.Contact;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;

@Repository
public class ContactRepositoryImpl extends GenericRepositoryImpl<Long, Contact> implements ContactRepository {

    @Override
    public Contact getByUserId(Long id) {
        String hql = "from Contact as C where C.user.id=:id";
        Query query = entityManager.createQuery(hql);
        query.setParameter("id", id);
        try {
            return (Contact) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}