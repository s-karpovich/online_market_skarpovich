package by.tut.mdcatalog.project2.repository;

import by.tut.mdcatalog.project2.repository.model.Role;

import java.sql.Connection;
import java.util.List;

public interface RoleRepository extends GenericRepository {

    List<Role> getAll();

    Role getById(Long id);
}