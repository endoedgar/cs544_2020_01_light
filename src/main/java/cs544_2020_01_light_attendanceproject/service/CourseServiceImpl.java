package cs544_2020_01_light_attendanceproject.service;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cs544_2020_01_light_attendanceproject.dao.CourseRepository;
import cs544_2020_01_light_attendanceproject.domain.Course;

@Service
public class CourseServiceImpl implements CourseService {
	private CourseRepository courseRepository;

	@Autowired
	public CourseServiceImpl(CourseRepository courseRepository) {
		this.courseRepository = courseRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Course> findCourseByName(String name) {
		return courseRepository.findByName(name);
	}

	@Override
	public Course saveCourse(@Valid Course course) {
		return courseRepository.save(course);
	}

	@Override
	public void deleteCourse(Course course) {
		courseRepository.delete(course);

	}

	@Override
	public Course updateCourse(Course course) {
		return courseRepository.save(course);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Course> listCourses() {
		return courseRepository.findAll();
	}

	@Override
	public Optional<Course> findCourseById(Long courseId) {
		return courseRepository.findById(courseId);
	}

}
