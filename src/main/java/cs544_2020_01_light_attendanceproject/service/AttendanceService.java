package cs544_2020_01_light_attendanceproject.service;

import cs544_2020_01_light_attendanceproject.dao.AttendanceDTO;
import cs544_2020_01_light_attendanceproject.domain.Attendance;
import cs544_2020_01_light_attendanceproject.domain.User;

public interface AttendanceService {

	public Iterable<AttendanceDTO> fetchAttendanceSummary(Long sessionId);

	public Iterable<AttendanceDTO> fetchAttendanceSummaryById(Long id);

	public Attendance createAttendance(Attendance attendance);

	public Iterable<User> fetchStudentAttendanceByCourseOffering(Long courseOfferingIdd);

	public Iterable<User> fetchStudentAttendanceBySession(Long sessionId);

	public Iterable<User> fetchStudentAttendanceByCourse(Long courseId);

	public Iterable<Attendance> fetchStudentAttendanceByStudent(Long studentId);

}
