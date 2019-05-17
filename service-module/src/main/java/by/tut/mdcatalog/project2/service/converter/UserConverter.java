package by.tut.mdcatalog.project2.service.converter;

import by.tut.mdcatalog.project2.repository.model.User;
import by.tut.mdcatalog.project2.service.model.UserDTO;

public interface UserConverter {

    UserDTO toDTO(User user);

    User fromDTO(UserDTO userDTO);
}
