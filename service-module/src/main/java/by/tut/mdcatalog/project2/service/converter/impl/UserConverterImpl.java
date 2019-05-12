package by.tut.mdcatalog.project2.service.converter.impl;

import by.tut.mdcatalog.project2.repository.model.Role;
import by.tut.mdcatalog.project2.repository.model.User;
import by.tut.mdcatalog.project2.service.converter.UserConverter;
import by.tut.mdcatalog.project2.service.model.UserDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserConverterImpl implements UserConverter {

    private final PasswordEncoder serviceEncoder;

    public UserConverterImpl(PasswordEncoder serviceEncoder) {
        this.serviceEncoder = serviceEncoder;
    }

    @Override
    public UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setFirstname(user.getFirstname());
        userDTO.setMiddlename(user.getMiddlename());
        userDTO.setSurname(user.getSurname());
        userDTO.setDeleted(user.getDeleted());
        return userDTO;
    }

    @Override
    public User fromDTO(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setPassword(serviceEncoder.encode(userDTO.getPassword()));
        user.setFirstname(userDTO.getFirstname());
        user.setMiddlename(userDTO.getMiddlename());
        user.setSurname(userDTO.getSurname());
        user.setDeleted(userDTO.getDeleted());
        Role role = new Role();
        role.setName(userDTO.getRole());
        user.setRole(role);
        return user;
    }
}
