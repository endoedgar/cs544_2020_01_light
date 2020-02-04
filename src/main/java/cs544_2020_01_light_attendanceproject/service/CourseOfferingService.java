package cs544_2020_01_light_attendanceproject.service;

import cs544_2020_01_light_attendanceproject.domain.CourseOffering;
import cs544_2020_01_light_attendanceproject.domain.Session;

import java.util.List;

public interface CourseOfferingService {
    public CourseOffering createOfferingCourse(CourseOffering courseOffering);
    public CourseOffering getCourseOffering(Long id);
    public List<CourseOffering> getAllCourseOffering();
    public void deleteAllOfferingCourse();
    public void deleteOfferingCourse(Long id );
    public CourseOffering updateCourseOffering(CourseOffering courseOffering);
    public List<Session> getSession(Long id);
}
