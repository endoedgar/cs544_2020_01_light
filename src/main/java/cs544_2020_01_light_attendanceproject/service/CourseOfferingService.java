package cs544_2020_01_light_attendanceproject.service;

import cs544_2020_01_light_attendanceproject.dao.CourseOfferingRepository;
import cs544_2020_01_light_attendanceproject.dao.CourseRepository;
import cs544_2020_01_light_attendanceproject.domain.CourseOffering;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class CourseOfferingService {
    @Autowired
    CourseOfferingRepository courseOfferingRepository;
    @Autowired
    CourseRepository courseRepository;
    @Transactional
    public CourseOffering createOfferingCourse(CourseOffering courseOffering){
        return courseOfferingRepository.save(courseOffering);
    }
    @Transactional
    public CourseOffering getCourseOffering(Long id) //throws CourseOfferingExeption
    {
        return courseOfferingRepository.getOne(id); // will modify later
    }
    @Transactional
    public List<CourseOffering> getAllCourseOffering() {
        return courseOfferingRepository.findAll();
    }
    @Transactional
    public void deleteOfferingCourse(CourseOffering course ){
        courseOfferingRepository.delete(course);
    }
    @Transactional
    public void deleteAllOfferingCourse(){
        courseOfferingRepository.deleteAll();
    }
    @Transactional
    public CourseOffering updateCourseOffering(CourseOffering courseOffering){
        return courseOfferingRepository.save(courseOffering);
    }
}
