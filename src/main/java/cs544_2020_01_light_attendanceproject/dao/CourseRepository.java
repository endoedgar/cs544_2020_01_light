package cs544_2020_01_light_attendanceproject.dao;

import cs544_2020_01_light_attendanceproject.domain.Course;
import cs544_2020_01_light_attendanceproject.domain.CourseOffering;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findCourseByName(String course);
}
