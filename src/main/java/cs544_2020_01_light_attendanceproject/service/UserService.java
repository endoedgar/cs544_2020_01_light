package cs544_2020_01_light_attendanceproject.service;

import cs544_2020_01_light_attendanceproject.domain.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

public interface UserService {
    public UserDetails loadUserByUsername(String username);
    public User findUserByUsername(String username);
    public User registerNewUserAccount(User account);
    public Iterable<User> listUsers();
}
