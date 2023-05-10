package org.agh.edu.pl.carrentalrestapi.service;

import org.agh.edu.pl.carrentalrestapi.entity.UserRole;
import org.agh.edu.pl.carrentalrestapi.exception.types.UserNotFoundException;
import org.agh.edu.pl.carrentalrestapi.exception.types.UserRoleNotFoundException;
import org.agh.edu.pl.carrentalrestapi.exception.types.UserRoleWithGivenTypeExistsException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserRoleService {
    Page<UserRole> getAllAvailableRoles(Pageable pageable);
    //TODO: put it inside user Controller
    Page<UserRole> getUnExistingDistinctUserRolesForUser(Long id, Pageable pageable)  throws UserNotFoundException;
    Page<UserRole> getExistingDistinctUserRolesForUser(Long id, Pageable pageable)  throws UserNotFoundException;
    Long addUserRole(UserRole userRole) throws UserRoleWithGivenTypeExistsException;
    void deleteUserRole(String type) throws UserRoleNotFoundException;
    Long updateUserRole(Long id, UserRole userRole) throws UserRoleNotFoundException;
    UserRole findRoleByType(String type) throws UserRoleNotFoundException;

}
