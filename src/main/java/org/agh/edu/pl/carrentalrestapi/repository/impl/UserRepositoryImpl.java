package org.agh.edu.pl.carrentalrestapi.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.agh.edu.pl.carrentalrestapi.entity.User;
import org.agh.edu.pl.carrentalrestapi.entity.UserRole;
import org.agh.edu.pl.carrentalrestapi.exception.UserNotFoundException;
import org.agh.edu.pl.carrentalrestapi.exception.UserRoleNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

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
    public Optional<User> findUserByLogin(String login) {
        TypedQuery<User> query = entityManager
                .createQuery("SELECT u FROM User u LEFT JOIN FETCH u.userRoles WHERE u.login = :login", User.class);

        TypedQuery<User> userTypedQuery = query
                .setParameter("login", login);

        User user = userTypedQuery
                .getSingleResult();
        return Optional.ofNullable(user);
    }
    @Transactional
    public Optional<User> findUserByEmail(String email) {
        TypedQuery<User> query = entityManager
                .createQuery("SELECT u FROM User u LEFT JOIN FETCH u.userRoles WHERE u.email = :email", User.class);

        TypedQuery<User> userTypedQuery = query.setParameter("email", email);

        User user = userTypedQuery
                .getSingleResult();
        return Optional.ofNullable(user);
    }

    @Transactional
    public void addRoleToUser(Long userId, Long roleId) throws UserNotFoundException, UserRoleNotFoundException {
        User user = entityManager.find(User.class, userId);
        if (user == null) {
            throw new UserNotFoundException(userId);
        }
        UserRole userRole = entityManager.find(UserRole.class, roleId);

        if (userRole == null) {
            throw new UserRoleNotFoundException(roleId);
        }

        user.getUserRoles().add(userRole);
        entityManager.merge(user);
    }
}
