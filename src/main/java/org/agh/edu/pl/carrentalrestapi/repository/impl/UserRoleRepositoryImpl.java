package org.agh.edu.pl.carrentalrestapi.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.agh.edu.pl.carrentalrestapi.model.UserRole;
import org.agh.edu.pl.carrentalrestapi.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class UserRoleRepositoryImpl {

    @PersistenceContext
    private EntityManager entityManager;

    public UserRoleRepositoryImpl(EntityManager entityManager
                                 ) {
        this.entityManager = entityManager;
    }

    @Transactional
    public List<UserRole> getUnExistingDistinctUserRolesForUser(Long id) {
        TypedQuery<UserRole> query = entityManager.createQuery("SELECT ur FROM UserRole ur WHERE ur.id NOT IN " +
                "(SELECT ur.id FROM UserRole ur JOIN ur.users u WHERE u.id = :id)", UserRole.class);
        TypedQuery<UserRole> typedQuery = query.setParameter("id", id);

        return typedQuery
                .getResultList();
    }

}
