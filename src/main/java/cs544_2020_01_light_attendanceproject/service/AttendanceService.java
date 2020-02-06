package cs544_2020_01_light_attendanceproject.service;

import cs544_2020_01_light_attendanceproject.dao.AttendanceDTO;
import cs544_2020_01_light_attendanceproject.domain.Attendance;

public interface AttendanceService {

	public Iterable<AttendanceDTO> fetchAttendanceSummary(Long sessionId, Long courseOfferingId, Long courseId);

	public Iterable<AttendanceDTO> fetchAttendanceSummaryById(Long id);

	public Attendance createAttendance(Attendance attendance);

}
