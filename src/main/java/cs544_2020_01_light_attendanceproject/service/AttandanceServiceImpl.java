package cs544_2020_01_light_attendanceproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cs544_2020_01_light_attendanceproject.dao.AttendanceDTO;
import cs544_2020_01_light_attendanceproject.dao.AttendanceRepository;
import cs544_2020_01_light_attendanceproject.domain.Attendance;
import cs544_2020_01_light_attendanceproject.domain.User;

@Service
public class AttandanceServiceImpl implements AttendanceService {

	@Autowired
	AttendanceRepository attendanceRepository;

	@Override
	public Iterable<User> fetchStudentAttendanceBySession(Long sessionId) {
		return attendanceRepository.fetchStudentAttendanceBySession(sessionId);
	}

	@Override
	public Iterable<User> fetchStudentAttendanceByCourseOffering(Long courseOfferingIdd) {
		return attendanceRepository.fetchStudentAttendanceByCourseOffering(courseOfferingIdd);
	}

	@Override
	public Iterable<User> fetchStudentAttendanceByCourse(Long courseId) {
		return attendanceRepository.fetchStudentAttendanceByCourse(courseId);
	}

	@Override
	public Iterable<AttendanceDTO> fetchAttendanceSummaryById(Long id) {
		return attendanceRepository.fetchAttandanceSummaryById(id);
	}

	@Override
	public Iterable<Attendance> fetchStudentAttendanceByStudent(Long studentId) {
		return attendanceRepository.fetchStudentAttendanceByStudentId(studentId);
	}

	@Override
	public Attendance createAttendance(Attendance attendance) {
		return attendanceRepository.save(attendance);
	}

	@Override
	public Iterable<AttendanceDTO> fetchAttendanceSummary(Long sessionId) {
		return attendanceRepository.fetchStudentAttendanceWithAverage(sessionId);
	}

}
