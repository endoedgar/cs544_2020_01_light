package cs544_2020_01_light_attendanceproject.service;

import cs544_2020_01_light_attendanceproject.domain.CourseOffering;
import cs544_2020_01_light_attendanceproject.domain.Session;

import java.util.List;
import java.util.Optional;

public interface CourseOfferingService {
    public CourseOffering createOfferingCourse(CourseOffering courseOffering);
    public Optional<CourseOffering> getCourseOffering(Long id);
    public Iterable<CourseOffering> getAllCourseOffering();
    public void deleteOfferingCourse(CourseOffering co);
    public CourseOffering updateCourseOffering(CourseOffering courseOffering);
}
