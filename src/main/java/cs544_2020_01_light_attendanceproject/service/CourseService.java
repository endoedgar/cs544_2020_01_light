package cs544_2020_01_light_attendanceproject.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import cs544_2020_01_light_attendanceproject.domain.Course;

public interface CourseService {
	    public Course saveCourse(Course course);
	    public Iterable<Course> listCourses();
	
		public Course updateCourse(Course course);
		public Optional<Course> findCourseByName(String name);
		void deleteCourse(Course course);
}
