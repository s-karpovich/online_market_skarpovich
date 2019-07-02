package by.tut.mdcatalog.project2.service.converter;

import by.tut.mdcatalog.project2.repository.model.Contact;
import by.tut.mdcatalog.project2.service.model.ContactDTO;

public interface ContactConverter {

    ContactDTO toDTO(Contact contact);

    Contact fromDTO(ContactDTO contactDTO);
}
