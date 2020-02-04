package cs544_2020_01_light_attendanceproject.service;

import cs544_2020_01_light_attendanceproject.domain.CourseOffering;

import java.util.List;

public interface CourseOfferingService {
    public CourseOffering createOfferingCourse(CourseOffering courseOffering);
    public CourseOffering getCourseOffering(Long id);
    public List<CourseOffering> getAllCourseOffering();
    public void deleteAllOfferingCourse();
    public void deleteOfferingCourse(CourseOffering course );
    public CourseOffering updateCourseOffering(CourseOffering courseOffering);
}
