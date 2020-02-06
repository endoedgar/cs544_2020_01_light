package cs544_2020_01_light_attendanceproject.dao;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import cs544_2020_01_light_attendanceproject.domain.Course;
import cs544_2020_01_light_attendanceproject.domain.CourseOffering;

public interface CourseOfferingRepository extends JpaRepository<CourseOffering, Long> {
    Boolean existsCourseOfferingByCourseAndStartDateLessThanEqualAndEndDateGreaterThanEqual(Course course, Date startDate, Date endDate);
}
