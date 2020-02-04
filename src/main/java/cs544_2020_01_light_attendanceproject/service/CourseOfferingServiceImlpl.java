package cs544_2020_01_light_attendanceproject.service;

import cs544_2020_01_light_attendanceproject.dao.CourseOfferingRepository;
import cs544_2020_01_light_attendanceproject.dao.CourseRepository;
import cs544_2020_01_light_attendanceproject.domain.CourseOffering;
import cs544_2020_01_light_attendanceproject.domain.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class CourseOfferingServiceImlpl implements CourseOfferingService {
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
        return courseOfferingRepository.getOne(id); //
    }
    @Transactional
    public List<CourseOffering> getAllCourseOffering() {
        return courseOfferingRepository.findAll();
    }
    @Transactional
    public void deleteOfferingCourse(Long id ){
        courseOfferingRepository.deleteById(id);
    }
    @Transactional
    public void deleteAllOfferingCourse(){
        courseOfferingRepository.deleteAll();
    }
    @Transactional
    public CourseOffering updateCourseOffering(CourseOffering courseOffering){
        return courseOfferingRepository.save(courseOffering);
    }
    //view session list
    @Transactional
    public List<Session> getSession(Long id){
        return courseOfferingRepository.getOne(id).getSession();
    }
}
