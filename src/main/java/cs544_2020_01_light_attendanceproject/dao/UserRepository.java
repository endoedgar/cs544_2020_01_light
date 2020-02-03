package cs544_2020_01_light_attendanceproject.dao;

import cs544_2020_01_light_attendanceproject.domain.User;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface UserRepository extends Repository<User, Long> {
    Optional<User> findUserByUsername(String username);
}
