package by.tut.mdcatalog.project2.service;


import by.tut.mdcatalog.project2.service.model.ContactDTO;

public interface ContactService {

    ContactDTO getByUserId(Long id);
}
