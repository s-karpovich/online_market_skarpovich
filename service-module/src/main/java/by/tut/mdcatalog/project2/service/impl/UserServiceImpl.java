package by.tut.mdcatalog.project2.service.impl;

import by.tut.mdcatalog.project2.repository.RoleRepository;
import by.tut.mdcatalog.project2.repository.UserRepository;
import by.tut.mdcatalog.project2.repository.model.Role;
import by.tut.mdcatalog.project2.repository.model.User;
import by.tut.mdcatalog.project2.service.constant.ServiceErrors;
import by.tut.mdcatalog.project2.service.converter.RoleConverter;
import by.tut.mdcatalog.project2.service.converter.UserConverter;
import by.tut.mdcatalog.project2.service.exception.ServiceException;
import by.tut.mdcatalog.project2.service.model.RoleDTO;
import by.tut.mdcatalog.project2.service.UserService;
import by.tut.mdcatalog.project2.service.model.RoleDTOUpdated;
import by.tut.mdcatalog.project2.service.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.SQLException;
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
                           RoleConverter roleConverter) {
        this.serviceEncoder = serviceEncoder;
        this.userConverter = userConverter;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.roleConverter = roleConverter;
    }

    @Override
    public List<UserDTO> getUsers() {
        try (Connection connection = userRepository.getConnection()) {
            try {
                connection.setAutoCommit(false);
                List<UserDTO> userDTOList = new ArrayList<>();
                List<User> userList = userRepository.getUsers(connection);
                for (User user : userList) {
                    Role role = roleRepository.getById(connection, user.getRole().getId());
                    RoleDTO roleDTO = roleConverter.toDTO(role);
                    UserDTO userDTO = userConverter.toDTO(user);
                    userDTO.setRole(roleDTO.getName());
                    userDTOList.add(userDTO);
                }
                connection.commit();
                return userDTOList;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new ServiceException(ServiceErrors.QUERY_FAILED, e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(ServiceErrors.DATABASE_CONNECTION_ERROR, e);
        }
    }

    @Override
    public UserDTO getByUsername(String username) {
        try (Connection connection = userRepository.getConnection()) {
            try {
                connection.setAutoCommit(false);
                User user = userRepository.getByUsername(connection, username);
                UserDTO getUserDTO = userConverter.toDTO(user);
                Role role = roleRepository.getById(connection, user.getRole().getId());
                getUserDTO.setRole(roleConverter.toDTO(role).getName());
                connection.commit();
                return getUserDTO;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new ServiceException(ServiceErrors.QUERY_FAILED, e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(ServiceErrors.DATABASE_CONNECTION_ERROR, e);
        }
    }

    @Override
    public void add(UserDTO userDTO) {
        User user = userConverter.fromDTO(userDTO);
        try (Connection connection = userRepository.getConnection()) {
            try {
                connection.setAutoCommit(false);
                userRepository.add(connection, user);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new ServiceException(ServiceErrors.QUERY_FAILED, e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(ServiceErrors.DATABASE_CONNECTION_ERROR, e);
        }
    }

    @Override
    public void updateUserRole(RoleDTOUpdated roleDTOUpdated) {
        try (Connection connection = userRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                userRepository.updateUserRole(connection, roleDTOUpdated.getRoleId(), roleDTOUpdated.getId());
                logger.info("User role successfully updated , user id{} , new role id {}",
                        roleDTOUpdated.getId(), roleDTOUpdated.getRoleId());
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new ServiceException(ServiceErrors.QUERY_FAILED, e);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(ServiceErrors.DATABASE_CONNECTION_ERROR, e);
        }
    }

    @Override
    public void deleteUsers(int[] ids) {
        try (Connection connection = userRepository.getConnection()) {
            try {
                connection.setAutoCommit(false);
                userRepository.deleteUsers(connection, ids);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new ServiceException(ServiceErrors.QUERY_FAILED, e);
            }
        } catch (
                SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(ServiceErrors.DATABASE_CONNECTION_ERROR, e);
        }
    }

    @Override
    public void resetPassword(Long id) {
        String generatedPassword = generatePassword(12);
        String encodedPassword = serviceEncoder.encode(generatedPassword);
        try (Connection connection = userRepository.getConnection()) {
            try {
                connection.setAutoCommit(false);
                userRepository.resetPassword(connection, encodedPassword, id);
                connection.commit();
                logger.info("Password generated for User (ID): {}. New password: {}", id, generatedPassword);
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new ServiceException(ServiceErrors.QUERY_FAILED, e);
            }
        } catch (
                SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(ServiceErrors.DATABASE_CONNECTION_ERROR, e);
        }
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



