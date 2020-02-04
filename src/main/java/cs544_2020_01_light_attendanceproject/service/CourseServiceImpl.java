package cs544_2020_01_light_attendanceproject.service;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cs544_2020_01_light_attendanceproject.dao.CourseRepository;
import cs544_2020_01_light_attendanceproject.dao.CourseRepository;
import cs544_2020_01_light_attendanceproject.domain.Course;

@Service
public class CourseServiceImpl implements CourseService {
	@Autowired
	 private CourseRepository CourseRepository;
	 
	@Override
	@Transactional
	public Course findCourseByName(String name) {
	       Optional<Course>course = CourseRepository.findByName(name);
	       return  course.get();
	}

	@Override
	public Course addNewCourse(@Valid Course course) {
		return CourseRepository.save(course);
	}

	@Override
	public void deleteCourse(Course course) {
		CourseRepository.delete(course);
		
	}

	@Override
	public Course replaceCourse(String name , Course course) {
		return CourseRepository.findByName(name).map(Course->{
			Course.setDescription(course.getDescription());
			Course.setName(course.getName());
			return CourseRepository.save(course);
		}).orElseGet(()->{
			course.setName(name);
		

		return CourseRepository.save(course);
	});}

	@Override
	public Iterable<Course> listCourse() {
		return CourseRepository.findAll();
	}



}
