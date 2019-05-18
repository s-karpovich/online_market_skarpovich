package by.tut.mdcatalog.project2.service.impl;

import by.tut.mdcatalog.project2.repository.ReviewRepository;
import by.tut.mdcatalog.project2.repository.RoleRepository;
import by.tut.mdcatalog.project2.repository.UserRepository;
import by.tut.mdcatalog.project2.repository.model.User;
import by.tut.mdcatalog.project2.service.UserService;
import by.tut.mdcatalog.project2.service.converter.RoleConverter;
import by.tut.mdcatalog.project2.service.converter.UserConverter;
import by.tut.mdcatalog.project2.service.model.UserDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.sql.Connection;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceUnitTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserConverter userConverter;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private RoleConverter roleConverter;
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private Connection connection;
    @Mock
    private PasswordEncoder serviceEncoder;
    @Mock
    private UserService userService;

    @Before
    public void init() {
        userService = new UserServiceImpl(serviceEncoder,
                userConverter,
                userRepository,
                reviewRepository,
                roleRepository,
                roleConverter);
    }

    @Test
    public void shouldReturnUserByUsername() {
        when(userRepository.getConnection()).thenReturn(connection);
        User user = new User();
        user.setFirstname("testfirstname");
        user.setSurname("testsurname");
        user.setUsername("testusername@mail.com");
        when(userRepository.getByUsername(connection, "testusername@mail.com")).thenReturn(user);
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstname("tesfirstname");
        userDTO.setSurname("testsurname");
        userDTO.setUsername("testusername");
        when(userConverter.toDTO(user)).thenReturn(userDTO);
        Assert.assertEquals(userDTO, userService.getByUsername("testusername@mail.com"));
    }

    @Test
    public void shouldReturnNullIfNullWhenReadUserByUsername()  {
        when(userRepository.getConnection()).thenReturn(connection);
        when(userRepository.getByUsername(connection, "testusername@mail.com")).thenReturn(null);
        Assert.assertNull(userService.getByUsername("testusername@mail.com"));
    }
}
