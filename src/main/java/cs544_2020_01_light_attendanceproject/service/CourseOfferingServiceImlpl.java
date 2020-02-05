package cs544_2020_01_light_attendanceproject.service;

import cs544_2020_01_light_attendanceproject.dao.CourseOfferingRepository;
import cs544_2020_01_light_attendanceproject.dao.CourseRepository;
import cs544_2020_01_light_attendanceproject.domain.CourseOffering;
import cs544_2020_01_light_attendanceproject.domain.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import javax.validation.Valid;
import javax.validation.ValidationException;
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
        validateCourseOfferingBusinessLogic(courseOffering);
        return courseOfferingRepository.save(courseOffering);
    }
    @Transactional(readOnly = true)
    public void validateCourseOfferingBusinessLogic(@Valid CourseOffering courseOffering) {
        if(courseOffering.getEndDate().compareTo(courseOffering.getStartDate()) < 0) {
            throw new ValidationException("Impossible Date interval.");
        }
        if (courseOfferingRepository.existsCourseOfferingByCourseAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                courseOffering.getCourse(), courseOffering.getStartDate(), courseOffering.getEndDate())) {
            throw new ValidationException("Invalid interval! It overlaps with another course offering of this course.");
        }
    }
    @Transactional(readOnly = true)
    public Optional<CourseOffering> getCourseOffering(Long id) {
        return courseOfferingRepository.findById(id);
    }
    @Transactional(readOnly = true)// return sessions
    public Optional<List<Session>> getCourseSessions(Long id) {
        return  courseOfferingRepository.findById(id).map(co ->
                Optional.ofNullable(co.getSessions())).orElse(Optional.empty());
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
        validateCourseOfferingBusinessLogic(courseOffering);
        return courseOfferingRepository.save(courseOffering);
    }

}
