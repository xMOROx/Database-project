package org.agh.edu.pl.carrentalrestapi.service.impl;

import org.agh.edu.pl.carrentalrestapi.entity.UserRole;
import org.agh.edu.pl.carrentalrestapi.exception.UserNotFoundException;
import org.agh.edu.pl.carrentalrestapi.exception.UserRoleNotFoundException;
import org.agh.edu.pl.carrentalrestapi.exception.UserRoleWithGivenTypeExistsException;
import org.agh.edu.pl.carrentalrestapi.repository.UserRepository;
import org.agh.edu.pl.carrentalrestapi.repository.UserRoleRepository;
import org.agh.edu.pl.carrentalrestapi.service.UserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service("userRoleService")
@Transactional
public class UserRoleServiceImpl implements UserRoleService {
    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository,
                               UserRepository userRepository) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<UserRole> getAllAvailableRoles() {
        return userRoleRepository.findAll();
    }

    @Override
    public List<UserRole> getUnExistingDistinctUserRolesForUser(Long id) throws UserNotFoundException {
        if(userRepository.findById(id).isEmpty())
            throw new UserNotFoundException(id);

        return userRoleRepository.findUnExistingDistinctUserRolesForUser(id);
    }

    @Override
    public Long addUserRole(UserRole userRole) throws UserRoleWithGivenTypeExistsException {
        if (userRoleRepository.findByType(userRole.getType()).isPresent())
            throw new UserRoleWithGivenTypeExistsException(userRole.getType());

        UserRole saved = userRoleRepository.save(userRole);

        return saved.getId();
    }

    @Override
    public void deleteUserRole(String type) throws UserRoleNotFoundException {
        Optional<UserRole> userRole = userRoleRepository.findByType(type);
        if (userRole.isEmpty())
            throw new UserRoleNotFoundException(type);

        UserRole userRoleToDelete = userRole.get();

        userRoleRepository.deleteById(userRoleToDelete.getId());
    }

    @Override
    public Long updateUserRole(Long id, UserRole userRole) throws UserRoleNotFoundException {
        Optional<UserRole> userRoleToUpdate = userRoleRepository.findById(id);
        if (userRoleToUpdate.isEmpty())
            throw new UserRoleNotFoundException(id);

        userRoleToUpdate.get().setType(userRole.getType());

        UserRole saved = userRoleRepository.save(userRoleToUpdate.get());

        return saved.getId();
    }
}
