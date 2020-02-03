package cs544_2020_01_light_attendanceproject.dao;

import cs544_2020_01_light_attendanceproject.domain.Attendance;
import org.springframework.data.repository.Repository;

public interface AttendanceRepository extends Repository<Attendance, Long> {
}
