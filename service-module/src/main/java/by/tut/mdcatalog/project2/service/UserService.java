package by.tut.mdcatalog.project2.service;

import by.tut.mdcatalog.project2.service.model.ContactDTO;
import by.tut.mdcatalog.project2.service.model.UserDTO;
import by.tut.mdcatalog.project2.service.model.RoleDTOUpdated;

import java.util.List;

public interface UserService {

    List<UserDTO> getUsers();

    UserDTO getByUsername(String username);

    void add(UserDTO userDTO);

    void updateUserRole(RoleDTOUpdated roleDTOUpdated);

    void updateProfile(UserDTO userDTO, ContactDTO contactDTO);

    void deleteUsers(int[] ids);

    void resetPassword(Long id);
}
