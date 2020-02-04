package cs544_2020_01_light_attendanceproject.controller;

import cs544_2020_01_light_attendanceproject.domain.CourseOffering;
import cs544_2020_01_light_attendanceproject.domain.Session;
import cs544_2020_01_light_attendanceproject.service.CourseOfferingServiceImlpl;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@RestController
@RequestMapping("/courseOffering")
public class CourseOfferingController {
    private CourseOfferingServiceImlpl courseOfferingServise;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)

    public CourseOffering createCourseOffering(@RequestBody @Valid CourseOffering courseOffering) {
        return courseOfferingServise.createOfferingCourse(courseOffering);
    }

    @GetMapping("/getAll")
    @Secured({ "ROLE_ADMIN", "ROLE_FACULTY", "ROLE_STUDENT" })// case 3 viewa all offering course
    public List<CourseOffering> viewAllOfferingCourse() {
        return courseOfferingServise.getAllCourseOffering();
    }

    @GetMapping("getSessions/{id}")
    @Secured({ "ROLE_ADMIN", "ROLE_FACULTY"}) // case 4 views all related session for the given course
    public List<Session> getSessions(@PathVariable Long id){
        return courseOfferingServise.getSession(id);
    }

    @GetMapping("/getOne/{id}")
    @Secured({ "ROLE_ADMIN", "ROLE_FACULTY", "ROLE_STUDENT" })
    public CourseOffering one(@PathVariable Long id) {
        return courseOfferingServise.getCourseOffering(id);
    }

    @DeleteMapping("/delete")
    @Secured({"ROLE_ADMIN"})
    public void deleteCourseOffering(Long id) {
        courseOfferingServise.deleteOfferingCourse(id);
    }

    @PutMapping("/update")
    @Secured({"ROLE_ADMIN"})
    public CourseOffering updateCourseOffering(@RequestBody @Valid CourseOffering courseOffering) {
        return courseOfferingServise.updateCourseOffering(courseOffering);
    }

}
