package org.agh.edu.pl.carrentalrestapi.service;

import org.agh.edu.pl.carrentalrestapi.entity.UserRole;

import java.util.List;

public interface UserRoleService {
    List<UserRole> getAllUserRoles();
    List<UserRole> getUnExistingDistinctUserRolesForUser(Long id);
    Long addUserRole(UserRole userRole);
    void deleteUserRole(String type);
    Long updateUserRole(Long id, UserRole userRole);

}
