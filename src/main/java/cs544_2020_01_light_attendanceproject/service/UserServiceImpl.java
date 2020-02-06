package cs544_2020_01_light_attendanceproject.service;

import java.util.Optional;

import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cs544_2020_01_light_attendanceproject.dao.UserRepository;
import cs544_2020_01_light_attendanceproject.domain.User;

@Service
public class UserServiceImpl implements UserService {
	private UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<User> findUserByUsername(String username) {
		return userRepository.findUserByUsername(username);
	}

	@Transactional(readOnly = true)
	public void validateUniqueUserFields(@Valid User account) {
		if (userRepository.existsUserByUsernameAndIdIsNot(account.getUsername(), account.getId())) {
			throw new ValidationException("Username " + account.getUsername() + " is already taken.");
		}
		if (userRepository.existsUserByBarCodeIdAndIdIsNot(account.getBarCodeId(), account.getId())) {
			throw new ValidationException("Barcode " + account.getBarCodeId() + " is already taken.");
		}
	}

	@Transactional
	@Override
	public User saveUser(@Valid User account) {
		validateUniqueUserFields(account);
		return userRepository.save(account);
	}

	@Transactional(readOnly = true)
	public Iterable<User> listUsers() {
		return userRepository.findAll();
	}

	@Transactional
	public void deleteUser(User user) {
		userRepository.delete(user);
	}

	@Transactional
	public User updateUser(@Valid User newUser) {
		validateUniqueUserFields(newUser);
		return userRepository.save(newUser);
	}

	@Transactional
	public User setUserPassword(String username, String newPassword) {
		return userRepository.findUserByUsername(username).map((user) -> {
			user.setPassword(newPassword);
			return userRepository.save(user);
		}).orElseThrow(() -> new UsernameNotFoundException("Invalid username."));
	}

	@Transactional(readOnly = true)
	@Override
	public User findUserById(Long userId) {
		return userRepository.findById(userId).get();
	}
}
