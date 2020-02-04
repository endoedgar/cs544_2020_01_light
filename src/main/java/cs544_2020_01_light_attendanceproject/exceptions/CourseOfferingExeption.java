package cs544_2020_01_light_attendanceproject.exceptions;

public class CourseOfferingExeption extends RuntimeException {
    public CourseOfferingExeption(Long id) {
        super("Course is not offering " + id);
    }

}
