package cs544_2020_01_light_attendanceproject.dao;

import java.io.Serializable;

import cs544_2020_01_light_attendanceproject.domain.Course;
import cs544_2020_01_light_attendanceproject.domain.CourseOffering;
import cs544_2020_01_light_attendanceproject.domain.Session;
import cs544_2020_01_light_attendanceproject.domain.User;

public class AttendanceDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private User student;
	private Session session;
	private CourseOffering courseOffering;
	private Course course;
	private Long average;

	public AttendanceDTO() {
	}

	public Long getAverage() {
		return average;
	}

	public void setAverage(Long average) {
		this.average = average;
	}

	public AttendanceDTO(User student, Session session, CourseOffering courseOffering, Course course, Long average) {
		super();
		this.student = student;
		this.session = session;
		this.courseOffering = courseOffering;
		this.course = course;
		this.average = average;
	}

	public AttendanceDTO(User student, Session session, CourseOffering courseOffering, Course course) {
		super();
		this.student = student;
		this.session = session;
		this.courseOffering = courseOffering;
		this.course = course;
	}

	public AttendanceDTO(User student, Session session, CourseOffering courseOffering) {
		super();
		this.student = student;
		this.session = session;
		this.courseOffering = courseOffering;
	}

	public User getStudent() {
		return student;
	}

	public void setStudent(User student) {
		this.student = student;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public CourseOffering getCourseOffering() {
		return courseOffering;
	}

	public void setCourseOffering(CourseOffering courseOffering) {
		this.courseOffering = courseOffering;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

}
