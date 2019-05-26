package by.tut.mdcatalog.project2.service.impl;

import by.tut.mdcatalog.project2.repository.RoleRepository;
import by.tut.mdcatalog.project2.repository.model.Role;
import by.tut.mdcatalog.project2.service.RoleService;
import by.tut.mdcatalog.project2.service.constant.ServiceErrors;
import by.tut.mdcatalog.project2.service.converter.RoleConverter;
import by.tut.mdcatalog.project2.service.exception.ServiceException;
import by.tut.mdcatalog.project2.service.model.RoleDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    private final static Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
    private final RoleRepository roleRepository;
    private final RoleConverter roleConverter;

    public RoleServiceImpl(RoleRepository roleRepository, RoleConverter roleConverter) {
        this.roleRepository = roleRepository;
        this.roleConverter = roleConverter;
    }

    @Override
    @Transactional
    public List<RoleDTO> getAll() {
        List<Role> roleNames = roleRepository.getAll();
        List<RoleDTO> roles = roleNames.stream()
                .map(roleConverter::toDTO)
                .collect(Collectors.toList());
        logger.info("Roles successfully requested from database:{}", roles.size());
        return roles;
    }
}

