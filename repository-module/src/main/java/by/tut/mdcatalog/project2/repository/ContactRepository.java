package by.tut.mdcatalog.project2.repository;

import by.tut.mdcatalog.project2.repository.model.Contact;

public interface ContactRepository extends GenericRepository<Long, Contact> {

    void merge(Contact contact);

    Contact getByUserId(Long id);
}
