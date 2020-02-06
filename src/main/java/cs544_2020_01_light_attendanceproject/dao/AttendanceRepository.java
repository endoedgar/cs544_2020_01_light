package cs544_2020_01_light_attendanceproject.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cs544_2020_01_light_attendanceproject.domain.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

	@Query("select new cs544_2020_01_light_attendanceproject.dao.AttendanceDTO"
			+ "(att.user, att.session, s.courseOffering, s.courseOffering.course, (count(att)/count(s))*100 )"
			+ " from Attendance att Join att.session s")
	List<AttendanceDTO> fetchAttandanceSummary();

	@Query("select new cs544_2020_01_light_attendanceproject.dao.AttendanceDTO"
			+ "(att.user, att.session, s.courseOffering, s.courseOffering.course, (count(att)/count(s))*100 )"
			+ " from Attendance att Join att.session s on s.id = :sessionId "
			+ "where s.courseOffering.id = :courseOfferingId and s.courseOffering.course.id = :courseId")
	List<AttendanceDTO> fetchStudentAttandanceSummary(@Param("sessionId") Long sessionId,
			@Param("courseOfferingId") Long courseOfferingId, @Param("courseId") Long courseId);

	@Query("select new cs544_2020_01_light_attendanceproject.dao.AttendanceDTO(att.user, att.session, att.session.courseOffering) FROM Attendance att where att.id = :id")
	Iterable<AttendanceDTO> fetchAttandanceSummaryById(@Param("id") Long id);

}
