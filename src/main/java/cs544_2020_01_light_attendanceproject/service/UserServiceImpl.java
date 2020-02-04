package cs544_2020_01_light_attendanceproject.service;

import cs544_2020_01_light_attendanceproject.domain.User;
import cs544_2020_01_light_attendanceproject.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public User findUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByUsername(username);

        user.orElseThrow(() -> new UsernameNotFoundException("Invalid username or password."));
        return user.get();
    }

    @Transactional
    @Override
    public User registerNewUserAccount(@Valid User account) {
        return userRepository.save(account);
    }

    @Transactional
    public Iterable<User> listUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public Optional<User> findOneUser(Long id) { return userRepository.findById(id); }

    @Transactional
    public Optional<User> findOneUser(String username) { return userRepository.findUserByUsername(username); }

    @Transactional
    public void deleteById(Long id) { userRepository.deleteById(id); }

    @Transactional
    public void deleteByUsername(String username) { userRepository.deleteUserByUsername(username); }

    @Transactional
    public User replaceUser(User newUser, String username) {
        return userRepository.findUserByUsername(username).map(user -> {
            user.setBarCodeId(newUser.getBarCodeId());
            user.setEmail(newUser.getEmail());
            user.setEnabled(newUser.isEnabled());
            user.setFirstName(newUser.getFirstName());
            user.setLastName(newUser.getLastName());
            user.setPassword(newUser.getPassword());
            user.setRoles(new HashSet<>(newUser.getRoles()));
            return userRepository.save(user);
        }).orElseGet(() -> {
            newUser.setUsername(username);
            return userRepository.save(newUser);
        });
    }

    @Transactional
    public User setUserPassword(String username, String newPassword) {
        User user = userRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Invalid username."));

        user.setPassword(newPassword);

        return userRepository.save(user);
    }
}
