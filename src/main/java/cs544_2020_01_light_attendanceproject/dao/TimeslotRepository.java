package cs544_2020_01_light_attendanceproject.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import cs544_2020_01_light_attendanceproject.domain.Timeslot;
import cs544_2020_01_light_attendanceproject.domain.User;

public interface TimeslotRepository extends CrudRepository<Timeslot, String> {
	//Optional<Timeslot> deleteByAbbrv(String abbr);
}
