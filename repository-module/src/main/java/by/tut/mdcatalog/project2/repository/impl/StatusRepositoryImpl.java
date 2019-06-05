package by.tut.mdcatalog.project2.repository.impl;

import by.tut.mdcatalog.project2.repository.StatusRepository;
import by.tut.mdcatalog.project2.repository.model.Status;
import org.springframework.stereotype.Repository;

@Repository
public class StatusRepositoryImpl extends GenericRepositoryImpl<Long, Status> implements StatusRepository {
}
