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
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserDetailsImpl(findUserByUsername(username));
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

    public Iterable<User> listUsers() {
        return userRepository.findAll();
    }
}
