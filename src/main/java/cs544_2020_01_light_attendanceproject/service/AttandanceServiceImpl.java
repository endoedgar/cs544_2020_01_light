package cs544_2020_01_light_attendanceproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cs544_2020_01_light_attendanceproject.dao.AttendanceDTO;
import cs544_2020_01_light_attendanceproject.dao.AttendanceRepository;
import cs544_2020_01_light_attendanceproject.domain.Attendance;

@Service
public class AttandanceServiceImpl implements AttendanceService {

	@Autowired
	AttendanceRepository attendanceRepository;

	@Override
	public Iterable<AttendanceDTO> fetchAttendanceSummary(Long sessionId, Long courseOfferingId, Long courseId) {
		return attendanceRepository.fetchAttandanceSummary();
	}

	@Override
	public Iterable<AttendanceDTO> fetchAttendanceSummaryById(Long id) {
		return attendanceRepository.fetchAttandanceSummaryById(id);
	}

	@Override
	public Attendance createAttendance(Attendance attendance) {
		return attendanceRepository.save(attendance);
	}

}
