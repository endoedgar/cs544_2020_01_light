package cs544_2020_01_light_attendanceproject.service;

import cs544_2020_01_light_attendanceproject.domain.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Optional;

public interface UserService {
    public Optional<User> findUserByUsername(String username);
    public User save(User account);
    public Iterable<User> listUsers();
    public void deleteUser(User user);
    public User replaceUser(User user);
    public User setUserPassword(String username, String newPassword);
}
