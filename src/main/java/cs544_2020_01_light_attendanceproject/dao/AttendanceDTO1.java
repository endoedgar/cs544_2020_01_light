package cs544_2020_01_light_attendanceproject.dao;

import java.io.Serializable;
import java.util.List;

import cs544_2020_01_light_attendanceproject.domain.User;

public class AttendanceDTO1 implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<User> students;
	private Long average;

	public AttendanceDTO1(List<User> student, Long average) {
		this.students = student;
		this.average = average;
	}

	public List<User> getStudents() {
		return students;
	}

	public void setStudents(List<User> students) {
		this.students = students;
	}

	public Long getAverage() {
		return average;
	}

	public void setAverage(Long average) {
		this.average = average;
	}

	public AttendanceDTO1() {

	}

}
