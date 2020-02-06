package cs544_2020_01_light_attendanceproject.service;

import java.util.Optional;

import cs544_2020_01_light_attendanceproject.domain.User;

public interface UserService {
    public Optional<User> findUserByUsername(String username);
    public User saveUser(User account);
    public Iterable<User> listUsers();
    public void deleteUser(User user);
    public User updateUser(User user);
    public User setUserPassword(String username, String newPassword);
	public User findUserById(Long userId);
}
