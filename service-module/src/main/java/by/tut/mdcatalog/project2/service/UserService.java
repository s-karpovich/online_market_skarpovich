package by.tut.mdcatalog.project2.service;

import by.tut.mdcatalog.project2.service.model.UserDTO;
import by.tut.mdcatalog.project2.service.model.RoleDTOUpdated;

import java.util.List;

public interface UserService {

    void create(UserDTO userDTO);

    void update(UserDTO userDTO);

    void updateUserRole(RoleDTOUpdated roleDTOUpdated);

    void deleteUsers(Long[] ids);

    List<UserDTO> getUsers();

    UserDTO getByUsername(String username);

    void resetPassword(Long id);
}
