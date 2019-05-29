package by.tut.mdcatalog.project2.service.impl;

import by.tut.mdcatalog.project2.repository.ContactRepository;
import by.tut.mdcatalog.project2.repository.RoleRepository;
import by.tut.mdcatalog.project2.repository.UserRepository;
import by.tut.mdcatalog.project2.repository.model.Contact;
import by.tut.mdcatalog.project2.repository.model.Role;
import by.tut.mdcatalog.project2.repository.model.User;
import by.tut.mdcatalog.project2.service.converter.ContactConverter;
import by.tut.mdcatalog.project2.service.converter.RoleConverter;
import by.tut.mdcatalog.project2.service.converter.UserConverter;
import by.tut.mdcatalog.project2.service.UserService;
import by.tut.mdcatalog.project2.service.model.ContactDTO;
import by.tut.mdcatalog.project2.service.model.RoleDTO;
import by.tut.mdcatalog.project2.service.model.RoleDTOUpdated;
import by.tut.mdcatalog.project2.service.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final PasswordEncoder serviceEncoder;
    private final UserConverter userConverter;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RoleConverter roleConverter;

    public UserServiceImpl(PasswordEncoder serviceEncoder,
                           UserConverter userConverter,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           RoleConverter roleConverter
    ) {
        this.serviceEncoder = serviceEncoder;
        this.userConverter = userConverter;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.roleConverter = roleConverter;
    }

    @Override
    @Transactional
    public void create(UserDTO userDTO) {
        User user = userConverter.fromDTO(userDTO);
        userRepository.persist(user);
    }

    @Override
    @Transactional
    public void update(UserDTO userDTO) {
        User user = userConverter.fromDTO(userDTO);
        userRepository.merge(user);
    }

    @Override
    @Transactional
    public void deleteUsers(Long[] ids) {
        for (Long id : ids) {
            User user = userRepository.getById(id);
            if (user != null && user.getDeleted()==false) {
                userRepository.delete(user);
            }
        }
    }

    @Override
    @Transactional
    public void updateUserRole(RoleDTOUpdated roleDTOUpdated) {
        userRepository.updateUserRole(roleDTOUpdated.getRoleId(), roleDTOUpdated.getId());
        logger.info("User role successfully updated , user id{} , new role id {}",
                roleDTOUpdated.getId(), roleDTOUpdated.getRoleId());
    }

    @Override
    @Transactional
    public List<UserDTO> getUsers() {
        List<UserDTO> userDTOList = new ArrayList<>();
        List<User> userList = userRepository.getAll();
        for (User user : userList) {
            Role role = roleRepository.getById(user.getRole().getId());
            RoleDTO roleDTO = roleConverter.toDTO(role);
            UserDTO userDTO = userConverter.toDTO(user);
            userDTO.setRoleDTO(roleDTO);
            userDTOList.add(userDTO);
        }
        return userDTOList;
    }

    @Override
    @Transactional
    public UserDTO getByUsername(String username) {
        User user = userRepository.getByUsername(username);
        UserDTO userDTO = userConverter.toDTO(user);
        Role role = roleRepository.getById(user.getRole().getId());
        userDTO.setRoleDTO(roleConverter.toDTO(role));
        return userDTO;
    }

    @Override
    @Transactional
    public void resetPassword(Long id) {
        String generatedPassword = generatePassword(12);
        String encodedPassword = serviceEncoder.encode(generatedPassword);
        userRepository.resetPassword(encodedPassword, id);
        logger.info("Password generated for User (ID): {}. New password: {}", id, generatedPassword);
    }

    private String generatePassword(int length) {
        Random random = new SecureRandom();
        final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder plainPassword = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            plainPassword.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }
        return new String(plainPassword);
    }
}



