package by.tut.mdcatalog.project2.service;

import by.tut.mdcatalog.project2.service.model.ContactDTO;
import by.tut.mdcatalog.project2.service.model.UserDTO;
import by.tut.mdcatalog.project2.service.model.RoleDTOUpdated;

import java.util.List;

public interface UserService {

    void create(UserDTO userDTO);

    void update(UserDTO userDTO);

    void updateUserRole(RoleDTOUpdated roleDTOUpdated);

    void updateProfile(UserDTO userDTO, ContactDTO contactDTO);

    void deleteUsers(Long[] ids);

    List<UserDTO> getAll();

    UserDTO getByUsername(String username);

    void resetPassword(Long id);
}
