package org.agh.edu.pl.carrentalrestapi.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.agh.edu.pl.carrentalrestapi.entity.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    public Page<UserRole> findUnExistingDistinctUserRolesForUser(Long id, Pageable pageable) {
        TypedQuery<UserRole> query = entityManager.createQuery("SELECT ur FROM UserRole ur WHERE ur.id NOT IN " +
                "(SELECT ur.id FROM UserRole ur JOIN ur.users u WHERE u.id = :id)", UserRole.class);
        TypedQuery<UserRole> typedQuery = query.setParameter("id", id);

        TypedQuery<Long> countQuery = entityManager.createQuery("SELECT COUNT(ur) FROM UserRole ur WHERE ur.id NOT IN " +
                "(SELECT ur.id FROM UserRole ur JOIN ur.users u WHERE u.id = :id)", Long.class);

        return new PageImpl<>(typedQuery.getResultList(), pageable, countQuery.getSingleResult());
    }

    @Transactional
    public Page<UserRole> findExistingDistinctUserRolesForUser(Long id, Pageable pageable) {
        TypedQuery<UserRole> query = entityManager.createQuery("SELECT ur FROM UserRole ur WHERE ur.id = :id", UserRole.class);
        TypedQuery<UserRole> typedQuery = query.setParameter("id", id);
        TypedQuery<Long> countQuery = entityManager.createQuery("SELECT COUNT(ur) FROM UserRole ur WHERE ur.id = :id", Long.class);
        return new PageImpl<>(typedQuery.getResultList(), pageable, countQuery.getSingleResult());
    }

}
