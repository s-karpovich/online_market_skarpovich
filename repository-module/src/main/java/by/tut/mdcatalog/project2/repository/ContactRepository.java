package by.tut.mdcatalog.project2.repository;


import by.tut.mdcatalog.project2.repository.model.Contact;

import java.sql.Connection;

public interface ContactRepository extends GenericRepository {

    Contact getByUserId(Connection connection, Long id);

    void update(Connection connection, Contact contact);
}
