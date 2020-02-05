package cs544_2020_01_light_attendanceproject.dao;

import cs544_2020_01_light_attendanceproject.domain.Course;
import cs544_2020_01_light_attendanceproject.domain.CourseOffering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.Date;

public interface CourseOfferingRepository extends JpaRepository<CourseOffering, Long> {
    Boolean existsCourseOfferingByCourseAndStartDateLessThanEqualAndEndDateGreaterThanEqual(Course course, Date startDate, Date endDate);
}
