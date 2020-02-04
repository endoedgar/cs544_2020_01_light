package cs544_2020_01_light_attendanceproject.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import cs544_2020_01_light_attendanceproject.domain.Course;

public interface CourseService {
	    public Course addNewCourse(Course course);
	    public Iterable<Course> listCourse();
	
		public Course replaceCourse(String name, Course course);
		public Course findCourseByName(String name);
		void deleteCourse(Course course);

		

}
