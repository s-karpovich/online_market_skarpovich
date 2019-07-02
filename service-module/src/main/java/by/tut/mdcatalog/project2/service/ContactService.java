package by.tut.mdcatalog.project2.service;

import by.tut.mdcatalog.project2.service.model.ContactDTO;

public interface ContactService {

    void update(ContactDTO contactDTO);

    ContactDTO getByUserId(Long id);
}
