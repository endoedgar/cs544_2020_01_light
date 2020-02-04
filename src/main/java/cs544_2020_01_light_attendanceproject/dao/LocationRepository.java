package cs544_2020_01_light_attendanceproject.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cs544_2020_01_light_attendanceproject.domain.Location;

@Repository
public interface LocationRepository extends CrudRepository<Location, Long> {
	Optional<Location> findByDescription(String description);
}
