package by.tut.mdcatalog.project2.repository.impl;

import by.tut.mdcatalog.project2.repository.model.Role;
import by.tut.mdcatalog.project2.repository.RoleRepository;
import org.springframework.stereotype.Repository;

@Repository
public class RoleRepositoryImpl extends GenericRepositoryImpl<Long, Role> implements RoleRepository {
}
