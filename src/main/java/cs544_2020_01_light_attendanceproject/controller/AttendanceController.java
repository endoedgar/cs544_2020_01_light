package cs544_2020_01_light_attendanceproject.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonView;

import cs544_2020_01_light_attendanceproject.dao.AttendanceDTO;
import cs544_2020_01_light_attendanceproject.domain.Attendance;
import cs544_2020_01_light_attendanceproject.domain.Course;
import cs544_2020_01_light_attendanceproject.domain.CourseOffering;
import cs544_2020_01_light_attendanceproject.domain.Location;
import cs544_2020_01_light_attendanceproject.domain.Session;
import cs544_2020_01_light_attendanceproject.domain.User;
import cs544_2020_01_light_attendanceproject.service.AttendanceService;
import cs544_2020_01_light_attendanceproject.service.CourseOfferingService;
import cs544_2020_01_light_attendanceproject.service.CourseService;
import cs544_2020_01_light_attendanceproject.service.LocationService;
import cs544_2020_01_light_attendanceproject.service.SessionService;
import cs544_2020_01_light_attendanceproject.service.UserService;

@RestController
@RequestMapping("/attendance")
@Secured({ "ROLE_FACULTY", "ROLE_ADMIN" })
public class AttendanceController {

	@Autowired
	private AttendanceService attendanceService;

	@Autowired
	private SessionService sessionService;
	@Autowired
	private LocationService locationService;
	@Autowired
	private UserService userService;

	@Autowired
	private CourseService courseService;

	@Autowired
	private CourseOfferingService courseOfferingService;

	@GetMapping("/session/{sessionId}")
	@JsonView(User.SummaryView.class)
	public Iterable<User> studentAttendanceBySession(@PathVariable Long sessionId) {
		Session session = sessionService.findSessionById(sessionId).get();
		Iterable<User> list = attendanceService.fetchStudentAttendanceBySession(session.getId());
		return list;
	}

	@GetMapping("/courseOffering/{courseOfferingId}")
	@JsonView(User.SummaryView.class)
	public Iterable<User> studentAttendanceByCourseOffering(@PathVariable Long courseOfferingId) {
		CourseOffering courseOffering = courseOfferingService.getCourseOffering(courseOfferingId).get();
		Iterable<User> list = attendanceService.fetchStudentAttendanceByCourseOffering(courseOffering.getId());
		return list;
	}

	@GetMapping("/course/{courseId}")
	@JsonView(User.SummaryView.class)
	public Iterable<User> studentAttendanceByCourse(@PathVariable Long courseId) {
		Course course = courseService.findCourseById(courseId).get();
		Iterable<User> list = attendanceService.fetchStudentAttendanceByCourse(course.getId());
		return list;
	}

	@GetMapping("/student/{studentId}")
	@JsonView(Attendance.SummaryView.class)
	public Iterable<Attendance> studentAttendanceByStudent(@PathVariable Long studentId) {
		User student = userService.findUserById(studentId);
		Iterable<Attendance> list = attendanceService.fetchStudentAttendanceByStudent(student.getId());
		return list;
	}

	@GetMapping("/{id}")
	public List<AttendanceDTO> retrieveAttendanceById(@PathVariable Long id) {
		return (List<AttendanceDTO>) attendanceService.fetchAttendanceSummaryById(id);
	}

	@GetMapping("/session/avg/{sessionId}")
	public List<AttendanceDTO> fetchAttendanceSummary(@PathVariable Long sessionId) {
		return (List<AttendanceDTO>) attendanceService.fetchAttendanceSummary(sessionId);
	}

	@PutMapping("/user/{userId}/session/{sessionId}/location/{locationId}")
	public ResponseEntity<Object> newAttendance(@PathVariable Long userId, @PathVariable Long sessionId,
			@PathVariable Long locationId) {
		Session session = sessionService.findSessionById(sessionId).get();
		User student = userService.findUserById(userId);
		Location location = locationService.findLocationById(locationId).get();
		Attendance attendance = new Attendance();
		attendance.setUser(student);
		attendance.setSession(session);
		attendance.setLocation(location);
		Attendance object = attendanceService.createAttendance(attendance);

		URI createdObject = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/user/{userId}/session/{sessionId}/location/{locationId}").buildAndExpand(object.getId())
				.toUri();

		return ResponseEntity.created(createdObject).build();

	}

}
