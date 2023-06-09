package org.agh.edu.pl.carrentalrestapi.service;

import org.agh.edu.pl.carrentalrestapi.entity.User;
import org.agh.edu.pl.carrentalrestapi.entity.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    Page<User> getAll(Pageable pageable);
    User getById(Long id);
    Long addUser(User user);
    Long fullUpdate(Long id, User user);
    Long partialUpdate(Long id, User user);
    void addRoleToUser(User user, UserRole role);
    void deleteRoleFromUser(User user, UserRole role);
    void deleteUser(Long id);
}
