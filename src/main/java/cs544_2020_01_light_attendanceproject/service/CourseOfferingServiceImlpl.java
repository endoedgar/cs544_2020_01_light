package cs544_2020_01_light_attendanceproject.service;

import cs544_2020_01_light_attendanceproject.dao.CourseOfferingRepository;
import cs544_2020_01_light_attendanceproject.dao.CourseRepository;
import cs544_2020_01_light_attendanceproject.domain.Course;
import cs544_2020_01_light_attendanceproject.domain.CourseOffering;
import cs544_2020_01_light_attendanceproject.domain.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CourseOfferingServiceImlpl implements CourseOfferingService {
    @Autowired
    CourseOfferingRepository courseOfferingRepository;
    @Autowired
    CourseRepository courseRepository;
    @Transactional
    public CourseOffering createOfferingCourse(CourseOffering courseOffering){
        return courseOfferingRepository.save(courseOffering);
    }
    @Transactional(readOnly = true)
    public Optional<CourseOffering> getCourseOffering(Long id) {
        return courseOfferingRepository.findById(id);
    }
    @Transactional(readOnly = true)
    public List<CourseOffering> getAllCourseOffering() {
        return courseOfferingRepository.findAll();
    }
    @Transactional
    public void deleteOfferingCourse(CourseOffering courseOffering){
        courseOfferingRepository.delete(courseOffering);
    }
    @Transactional
    public CourseOffering updateCourseOffering(CourseOffering courseOffering){
        return courseOfferingRepository.save(courseOffering);
    }
    //view session list
    @Transactional
    public List<Session> getSessions(Long id){
        return courseOfferingRepository.getOne(id).getSessions();
    }
}
