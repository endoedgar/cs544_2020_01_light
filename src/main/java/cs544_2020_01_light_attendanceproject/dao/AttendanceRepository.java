package cs544_2020_01_light_attendanceproject.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cs544_2020_01_light_attendanceproject.domain.Attendance;
import cs544_2020_01_light_attendanceproject.domain.User;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

	@Query("select student from User student join student.attendances attendance where attendance.session.id = :sessionId")
	List<User> fetchStudentAttendanceBySession(@Param("sessionId") Long sessionId);

	@Query("select student from User student join student.attendances attendance where attendance.session.courseOffering.id = :courseOfferingId")
	List<User> fetchStudentAttendanceByCourseOffering(@Param("courseOfferingId") Long courseOfferingId);

	@Query("select student from User student join student.attendances attendance where attendance.session.courseOffering.course.id = :courseId")
	List<User> fetchStudentAttendanceByCourse(@Param("courseId") Long courseId);

	@Query("select attendance from Attendance attendance where attendance.user.id = :studentId")
	List<Attendance> fetchStudentAttendanceByStudentId(@Param("studentId") Long studentId);

	@Query("select new cs544_2020_01_light_attendanceproject.dao.AttendanceDTO" + "(student, count(student) )"
			+ " from User student join student.attendances attendance where attendance.session.id = :sessionId")
	List<AttendanceDTO> fetchStudentAttendanceWithAverage(@Param("sessionId") Long sessionId);

	@Query("select new cs544_2020_01_light_attendanceproject.dao.AttendanceDTO"
			+ "(att.user, att.session, s.courseOffering, s.courseOffering.course, (count(att)/count(s))*100 )"
			+ " from Attendance att Join att.session s on s.id = :sessionId "
			+ "where s.courseOffering.id = :courseOfferingId and s.courseOffering.course.id = :courseId")
	List<AttendanceDTO> fetchStudentAttandanceSummary(@Param("sessionId") Long sessionId,
			@Param("courseOfferingId") Long courseOfferingId, @Param("courseId") Long courseId);

	@Query("select new cs544_2020_01_light_attendanceproject.dao.AttendanceDTO(att.user, att.session, att.session.courseOffering) FROM Attendance att where att.id = :id")
	Iterable<AttendanceDTO> fetchAttandanceSummaryById(@Param("id") Long id);

}
