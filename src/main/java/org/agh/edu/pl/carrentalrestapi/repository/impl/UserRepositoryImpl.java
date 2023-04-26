package org.agh.edu.pl.carrentalrestapi.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.agh.edu.pl.carrentalrestapi.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserRepositoryImpl {

    @PersistenceContext
    private EntityManager entityManager;

    private PasswordEncoder passwordEncoder;

    public UserRepositoryImpl(EntityManager entityManager,
                              PasswordEncoder passwordEncoder) {
        this.entityManager = entityManager;
        this.passwordEncoder = passwordEncoder;
    }
    @Transactional
    public User getUserByLogin(String login) {
        TypedQuery<User> query = entityManager
                .createQuery("SELECT u FROM User u LEFT JOIN FETCH u.userRoles WHERE u.login = :login", User.class);

        TypedQuery<User> userTypedQuery = query
                .setParameter("login", login);

        return userTypedQuery
                .getSingleResult();
    }
    @Transactional
    public User getUserByEmail(String email) {
        TypedQuery<User> query = entityManager
                .createQuery("SELECT u FROM User u LEFT JOIN FETCH u.userRoles WHERE u.email = :email", User.class);

        TypedQuery<User> userTypedQuery = query.setParameter("email", email);

        return userTypedQuery
                .getSingleResult();
    }

    @Transactional
    public void addRoleToUser(Long userId, Long roleId) {

    }
}
