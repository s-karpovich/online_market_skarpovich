package by.tut.mdcatalog.project2.service.impl;

import by.tut.mdcatalog.project2.repository.ContactRepository;
import by.tut.mdcatalog.project2.repository.UserRepository;
import by.tut.mdcatalog.project2.repository.model.Contact;
import by.tut.mdcatalog.project2.repository.model.User;
import by.tut.mdcatalog.project2.service.ContactService;
import by.tut.mdcatalog.project2.service.converter.ContactConverter;
import by.tut.mdcatalog.project2.service.converter.UserConverter;
import by.tut.mdcatalog.project2.service.model.ContactDTO;
import by.tut.mdcatalog.project2.service.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ContactServiceImpl implements ContactService {

    private static final Logger logger = LoggerFactory.getLogger(ContactServiceImpl.class);
    private final ContactConverter contactConverter;
    private final ContactRepository contactRepository;
    private final UserConverter userConverter;
    private final UserRepository userRepository;

    public ContactServiceImpl(
            ContactConverter contactConverter,
            ContactRepository contactRepository,
            UserRepository userRepository,
            UserConverter userConverter) {
        this.contactConverter = contactConverter;
        this.contactRepository = contactRepository;
        this.userConverter = userConverter;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void update(ContactDTO contactDTO) {
        Contact contact = contactConverter.fromDTO(contactDTO);
        contactRepository.merge(contact);
    }

    @Override
    @Transactional
    public ContactDTO getByUserId(Long id) {
        Contact contact = contactRepository.getByUserId(id);
        ContactDTO contactDTO = contactConverter.toDTO(contact);
        User user = userRepository.getById(contact.getUser().getId());
        UserDTO userDTO = userConverter.toDTO(user);
        contactDTO.setUserDTO(userDTO);
        return contactDTO;
    }
}
