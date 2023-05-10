package org.agh.edu.pl.carrentalrestapi.repository;

import org.agh.edu.pl.carrentalrestapi.entity.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository("userRoleRepository")
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    Page<UserRole> findUnExistingDistinctUserRolesForUser(Long id, Pageable pageable);
    @Query("SELECT ur FROM UserRole ur WHERE ur.type = ?1")
    @Transactional
    Optional<UserRole> findByType(String type);

}
