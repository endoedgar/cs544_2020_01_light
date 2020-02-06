package cs544_2020_01_light_attendanceproject.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cs544_2020_01_light_attendanceproject.domain.Course;

@org.springframework.stereotype.Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
	Optional<Course> findByName(String name);

}
