package cs544_2020_01_light_attendanceproject.controller;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonView;
import cs544_2020_01_light_attendanceproject.domain.User;
import cs544_2020_01_light_attendanceproject.exceptions.ItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import cs544_2020_01_light_attendanceproject.domain.Course;
import cs544_2020_01_light_attendanceproject.service.CourseService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/course")
public class CourseController {
	private CourseService courseService;

	@PostMapping
	@Secured(value = {"ROLE_ADMIN"})
	public ResponseEntity<Object> addNewCourse(@RequestBody @Valid Course course) {
		courseService.saveCourse(course);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(course.getName())
				.toUri();

		return ResponseEntity.created(location).build();
	}

	@GetMapping
	@JsonView(Course.SummaryView.class)
	public Iterable<Course> all() {
		return courseService.listCourses();
	}

	@Autowired
	public CourseController(CourseService courseService) {
		this.courseService=courseService;
	}

	@DeleteMapping("/{name}")
	@Secured(value = {"ROLE_ADMIN"})
	public Course deleteCourse(@PathVariable String name) {
		return courseService.findCourseByName(name).map(c -> {
					courseService.deleteCourse(c);
					return c;
				}).orElse(null);
	}

	@PutMapping("/{name}")
	@Secured(value = {"ROLE_ADMIN"})
	public Course updateCourse(@PathVariable String name, @RequestBody @Valid Course newCourse) {
		Course oldCourse = courseService.findCourseByName(name).orElse(newCourse);
		oldCourse.setName(newCourse.getName());
		oldCourse.setDescription(newCourse.getDescription());
		return courseService.updateCourse(oldCourse);
	}

	@GetMapping("/{name}")
	@JsonView(Course.DetailView.class)
	public Course findCourseByName(@PathVariable String name) {
		return courseService.findCourseByName(name).orElseThrow(() -> new ItemNotFoundException(name, Course.class));
	}
}
