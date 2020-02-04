package cs544_2020_01_light_attendanceproject.dao;

import cs544_2020_01_light_attendanceproject.domain.Course;
import cs544_2020_01_light_attendanceproject.domain.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

@org.springframework.stereotype.Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
//	  Optional<Course> findCourseById(long courseId);
//	    Optional<Course> deleteCourseById(long courseId);
//		void delete(String name);
		Optional<Course> findByName(String name);
		
		
		
}
