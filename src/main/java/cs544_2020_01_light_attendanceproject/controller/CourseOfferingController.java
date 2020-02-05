package cs544_2020_01_light_attendanceproject.controller;

import cs544_2020_01_light_attendanceproject.domain.*;
import cs544_2020_01_light_attendanceproject.exceptions.ItemNotFoundException;
import cs544_2020_01_light_attendanceproject.service.CourseOfferingService;
import cs544_2020_01_light_attendanceproject.service.CourseOfferingServiceImlpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
@RestController
@RequestMapping("/courseOffering")
public class CourseOfferingController {
    private CourseOfferingService courseOfferingServise;

    @Autowired
    public CourseOfferingController(CourseOfferingService courseOfferingService) {
        this.courseOfferingServise = courseOfferingService;
    }

    @PostMapping
    public ResponseEntity<Object> createCourseOffering(@RequestBody @Valid CourseOffering courseOffering) {
        courseOfferingServise.createOfferingCourse(courseOffering);

        URI locationUrl = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(courseOffering.getId())
                .toUri();

        return ResponseEntity.created(locationUrl).build();
    }

    @GetMapping
    @Secured({ "ROLE_ADMIN", "ROLE_FACULTY", "ROLE_STUDENT" })// case 3 viewa all offering course
    public Iterable<CourseOffering> viewAllOfferingCourse() {
        return courseOfferingServise.getAllCourseOffering();
    }

    @GetMapping("/{id}")
    @Secured({ "ROLE_ADMIN", "ROLE_FACULTY", "ROLE_STUDENT" })
    public CourseOffering fetchCourseOffering(@PathVariable Long id) {
        return courseOfferingServise.getCourseOffering(id).orElseThrow(() -> new ItemNotFoundException(id.toString(), CourseOffering.class));
    }

    @DeleteMapping("/{id}")
    @Secured({"ROLE_ADMIN"})
    public CourseOffering deleteCourseOffering(@PathVariable Long id) {
        return courseOfferingServise.getCourseOffering(id).map(co -> {
            courseOfferingServise.deleteOfferingCourse(co);
            return co;
        }).orElse(null);
    }

    @PutMapping("/{id}")
    @Secured({"ROLE_ADMIN"})
    public CourseOffering updateCourseOffering(@RequestBody @Valid CourseOffering newCourseOffering, @PathVariable Long id) {
        CourseOffering oldCourseOffering = courseOfferingServise.getCourseOffering(id).orElse(newCourseOffering);
        oldCourseOffering.setCourse(newCourseOffering.getCourse());
        oldCourseOffering.setEndDate(newCourseOffering.getEndDate());
        oldCourseOffering.setSessions(newCourseOffering.getSessions());
        oldCourseOffering.setStartDate(newCourseOffering.getStartDate());
        return courseOfferingServise.updateCourseOffering(oldCourseOffering);
    }

}
