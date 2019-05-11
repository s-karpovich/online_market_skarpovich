package by.tut.mdcatalog.project2.service.converter;

import by.tut.mdcatalog.project2.repository.model.Role;
import by.tut.mdcatalog.project2.service.model.RoleDTO;

public interface RoleConverter {

    RoleDTO toDTO(Role role);

    Role fromDTO(RoleDTO roleDTO);
}
