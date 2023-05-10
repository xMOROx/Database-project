package org.agh.edu.pl.carrentalrestapi.service.impl;

import org.agh.edu.pl.carrentalrestapi.entity.User;
import org.agh.edu.pl.carrentalrestapi.entity.Vehicle;
import org.agh.edu.pl.carrentalrestapi.exception.*;
import org.agh.edu.pl.carrentalrestapi.repository.UserRepository;
import org.agh.edu.pl.carrentalrestapi.repository.VehicleRepository;
import org.agh.edu.pl.carrentalrestapi.service.UserService;

import java.awt.print.Pageable;
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
    public List<User> getAll(){return userRepository.findAll();}

    @Override
    public Page<User> getAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User getById(Long id) throws VehicleNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new VehicleNotFoundException(id));
    }

    @Override
    public Long addUser(User user){
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
    public void deleteUser(Long id) throws UserNotFoundException {
        if (!userRepository.existsById(id))
            throw new UserNotFoundException(id);

        userRepository.deleteById(id);
    }
}
