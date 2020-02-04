package cs544_2020_01_light_attendanceproject.controller;

import cs544_2020_01_light_attendanceproject.domain.CourseOffering;
import cs544_2020_01_light_attendanceproject.service.CourseOfferingServisce;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/courseOffering")
public class CourseOfferingController {
    private CourseOfferingServisce courseOfferingServisce;

    @PostMapping("/course")
    @ResponseStatus(HttpStatus.CREATED)
    public CourseOffering createCourseOffering(@RequestBody @Valid CourseOffering courseOffering) {
        return courseOfferingServisce.createOfferingCourse(courseOffering);
    }

    @GetMapping("/course")
    public List<CourseOffering> all() {
        return courseOfferingServisce.getAllCourseOffering();
    }

    @GetMapping("/course/{id}")
    public CourseOffering one(@PathVariable Long id) {
        return courseOfferingServisce.getCourseOffering(id);
    }

    @DeleteMapping("/course/delete")
    public void deleteCourseOffering(CourseOffering courseOffering) {
        courseOfferingServisce.deleteOfferingCourse(courseOffering);
    }

    @PutMapping("/user/update")
    public CourseOffering updateCourseOffering(@RequestBody @Valid CourseOffering courseOffering) {
        return courseOfferingServisce.updateCourseOffering(courseOffering);
    }
}
