package org.agh.edu.pl.carrentalrestapi.repository;

import org.agh.edu.pl.carrentalrestapi.model.User;
import org.agh.edu.pl.carrentalrestapi.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository("userRoleRepository")
public interface UserRoleRepository extends JpaRepository<User, Long> {
    List<UserRole> getUnExistingDistinctUserRolesForUser(Long id);

}
