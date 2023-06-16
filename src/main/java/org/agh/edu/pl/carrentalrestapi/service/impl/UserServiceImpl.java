package org.agh.edu.pl.carrentalrestapi.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.agh.edu.pl.carrentalrestapi.entity.User;
import org.agh.edu.pl.carrentalrestapi.entity.UserRole;
import org.agh.edu.pl.carrentalrestapi.exception.*;
import org.agh.edu.pl.carrentalrestapi.exception.types.UserNotFoundException;
import org.agh.edu.pl.carrentalrestapi.exception.types.UserWithEmailExistsException;
import org.agh.edu.pl.carrentalrestapi.exception.types.UserWithLoginExistsException;
import org.agh.edu.pl.carrentalrestapi.repository.UserRepository;
import org.agh.edu.pl.carrentalrestapi.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userService")
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Page<User> getAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User getById(Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public Long addUser(User user) throws UserWithEmailExistsException, UserWithLoginExistsException {
        String login = user.getLogin();
        String email = user.getEmail();

        if (userRepository.findUserByEmail(email).isPresent())
            throw new UserWithEmailExistsException(email);
        if (userRepository.findUserByLogin(login).isPresent())
            throw new UserWithLoginExistsException(login);

        User saved = userRepository.save(user);

        return saved.getId();
    }

    @Override
    public Long fullUpdate(Long id, User user) throws UserWithEmailExistsException {
        User toUpdate;
        try {
            toUpdate = getById(id);
        } catch (UserNotFoundException e) {
            return addUser(user);
        }

        String email = user.getEmail();
        if (userRepository.findUserByEmail(email).isPresent() && !toUpdate.getEmail().equals(email)) {
            throw new UserWithEmailExistsException(email);
        }

        toUpdate.setEmail(email);
        toUpdate.setFirstName(user.getFirstName());
        toUpdate.setSurName(user.getSurName());
        toUpdate.setLogin(user.getLogin());
        toUpdate.setPesel(user.getPesel());
        toUpdate.setPhoneNumber(user.getPhoneNumber());
        User saved = userRepository.save(toUpdate);

        return saved.getId();
    }

    @Override
    public Long partialUpdate(Long id, User user) throws UserNotFoundException, UserWithEmailExistsException {
        User toUpdate = getById(id);

        if (user.getEmail() != null) {
            String email = user.getEmail();

            if (userRepository.findUserByEmail(email).isPresent() && !toUpdate.getEmail().equals(email)) {
                throw new UserWithEmailExistsException(email);
            }
            toUpdate.setEmail(email);
        }

        if (user.getLogin() != null) {
            toUpdate.setLogin(user.getLogin());
        }
        if (user.getFirstName() != null) {
            toUpdate.setFirstName(user.getFirstName());
        }
        if (user.getSurName() != null) {
            toUpdate.setSurName(user.getSurName());
        }
        if (user.getPesel() != null) {
            toUpdate.setPesel(user.getPesel());
        }
        if (user.getPhoneNumber() != null) {
            toUpdate.setPhoneNumber(user.getPhoneNumber());
        }

        User saved = userRepository.save(toUpdate);
        return saved.getId();
    }

    @Override
    public void deleteUser(Long id) throws UserNotFoundException {
        if (!userRepository.existsById(id))
            throw new UserNotFoundException(id);

        userRepository.deleteById(id);
    }

    @Override
    public void addRoleToUser(User user, UserRole role) {
        user.getUserRoles().add(role);
        userRepository.save(user);
    }

    @Override
    public void deleteRoleFromUser(User user, UserRole role) {
        user.getUserRoles().remove(role);
        userRepository.save(user);
    }
}
