package org.agh.edu.pl.carrentalrestapi.repository;

import org.agh.edu.pl.carrentalrestapi.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository("userRoleRepository")
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    List<UserRole> findUnExistingDistinctUserRolesForUser(Long id);

}
