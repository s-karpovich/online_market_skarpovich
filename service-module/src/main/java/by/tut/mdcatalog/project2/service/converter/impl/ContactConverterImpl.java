package by.tut.mdcatalog.project2.service.converter.impl;

import by.tut.mdcatalog.project2.repository.model.Contact;
import by.tut.mdcatalog.project2.service.converter.ContactConverter;
import by.tut.mdcatalog.project2.service.converter.UserConverter;
import by.tut.mdcatalog.project2.service.model.ContactDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContactConverterImpl implements ContactConverter {

    private final UserConverter userConverter;

    @Autowired
    public ContactConverterImpl(UserConverter userConverter) {
        this.userConverter = userConverter;
    }
    
    @Override
    public ContactDTO toDTO(Contact contact) {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setId(contact.getId());
        contactDTO.setAddress(contact.getAddress());
        contactDTO.setPhone(contact.getPhone());
        contactDTO.setUserDTO(userConverter.toDTO(contact.getUser()));
        return contactDTO;
    }

    @Override
    public Contact fromDTO(ContactDTO contactDTO) {
        Contact contact = new Contact();
        contact.setId(contactDTO.getId());
        contact.setAddress(contactDTO.getAddress());
        contact.setPhone(contactDTO.getPhone());
        contact.setUser(userConverter.fromDTO(contactDTO.getUserDTO()));
        return contact;
    }
}
