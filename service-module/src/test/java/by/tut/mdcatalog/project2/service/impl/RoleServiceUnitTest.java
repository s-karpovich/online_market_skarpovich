package by.tut.mdcatalog.project2.service.impl;

import by.tut.mdcatalog.project2.repository.RoleRepository;
import by.tut.mdcatalog.project2.repository.model.Role;
import by.tut.mdcatalog.project2.service.RoleService;
import by.tut.mdcatalog.project2.service.converter.RoleConverter;
import by.tut.mdcatalog.project2.service.model.RoleDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RoleServiceUnitTest {
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private RoleConverter roleConverter;
    @Mock
    private Connection connection;
    private RoleService roleService;
    private Role firstRole = new Role(1L, "ROLE1");
    private Role secondRole = new Role(2L, "ROLE2");
    private List<Role> roleList = Arrays.asList(firstRole, secondRole);

    @Before
    public void init() {
        roleService = new RoleServiceImpl(roleRepository, roleConverter);
    }

    @Test
    public void shouldReturnCorrectRoleDTOFromRole()  {
        when(roleRepository.getConnection()).thenReturn(connection);
        when(roleRepository.getRoles(connection)).thenReturn(roleList);
        when(roleConverter.toDTO(firstRole)).thenReturn(new RoleDTO(firstRole.getId(), firstRole.getName()));
        when(roleConverter.toDTO(secondRole)).thenReturn(new RoleDTO(secondRole.getId(), secondRole.getName()));
        Assert.assertEquals("ROLE1", roleService.getRoles().get(0).getName());
        Assert.assertEquals("ROLE2", roleService.getRoles().get(1).getName());
    }
}
