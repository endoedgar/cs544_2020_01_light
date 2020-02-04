package cs544_2020_01_light_attendanceproject.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cs544_2020_01_light_attendanceproject.domain.Course;
import cs544_2020_01_light_attendanceproject.service.CourseService;

@RestController
public class CourseController {

	private CourseService courseService;
	
	
	

	@PutMapping("/course")
	@ResponseStatus(HttpStatus.CREATED)
	public Course addNewCourse(@RequestBody @Valid Course course) {
		return courseService.addNewCourse(course);
	}

	@GetMapping("/course")
	public Iterable<Course> all() {
		return courseService.listCourse();
	}

	public CourseController(CourseService courseService) {
		this.courseService=courseService;
		
	}

	@DeleteMapping("/course/{name}")
	public void deleteCourse(@PathVariable String name) {
		Course course= courseService.findCourseByName(name);
		
		courseService.deleteCourse(course);
	}

	@PutMapping("/course/{name}")
	public Course replaceCourse(String name, Course course) {
		return courseService.replaceCourse(name, course);
	}

	@GetMapping("/course/{name}")
	public Course findCourseByName(String name) {
		return courseService.findCourseByName(name);
	}

}
