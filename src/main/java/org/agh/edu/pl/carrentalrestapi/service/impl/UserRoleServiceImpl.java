package org.agh.edu.pl.carrentalrestapi.service.impl;

import org.agh.edu.pl.carrentalrestapi.entity.UserRole;
import org.agh.edu.pl.carrentalrestapi.repository.UserRepository;
import org.agh.edu.pl.carrentalrestapi.repository.UserRoleRepository;
import org.agh.edu.pl.carrentalrestapi.service.UserRoleService;

import java.util.List;

public class UserRoleServiceImpl implements UserRoleService {
    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository,
                               UserRepository userRepository) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<UserRole> getAllUserRoles() {
        return userRoleRepository.findAll();
    }

    @Override
    public List<UserRole> getUnExistingDistinctUserRolesForUser(Long id) {
        if(userRepository.findById(id).isEmpty())
            throw new RuntimeException("User with id: " + id + " does not exist");

        return userRoleRepository.findUnExistingDistinctUserRolesForUser(id);
    }
}
