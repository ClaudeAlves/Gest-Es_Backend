package dev.claude.service;

import dev.claude.domain.user.EnumRole;
import dev.claude.domain.user.Role;
import dev.claude.repository.user.RoleRepository;
import dev.claude.service.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class RoleService {
    @Autowired
    private RoleRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    /**
     * Retrieves a valid Role from the DB. THis is the only way to get a role to use
     * with a user. To be coherent with the DB.
     *
     * @param roleName the name of the role to retrieve
     * @return the role from DB
     * @throws EntityDoesNotExistException if the role is not in the DB. (Which is bad).
     */
    public Role getRole(EnumRole roleName) throws EntityDoesNotExistException {
        return repository.findByName(roleName).orElseThrow(
                () -> new EntityDoesNotExistException("Role with name " + roleName + " doesn't exist in DB."));
    }

    /**
     * This method is called to Generate the Roles to the DB. This is used to
     * persist the role to the DB.
     */
    public void generateRolesToDatabase() {
        try {
            List<Role> roles = new ArrayList<>();
            for (EnumRole eRole : EnumRole.values()) {
                Role role = Role.builder().name(eRole).build();
                roles.add(role);
            }

            for (Role role : roles) {
                if (!repository.existsByName(role.getName())) {
                    repository.save(role);
                }
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}

