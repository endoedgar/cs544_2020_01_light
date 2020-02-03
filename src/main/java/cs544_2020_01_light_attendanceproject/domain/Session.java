package cs544_2020_01_light_attendanceproject.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private CourseOffering courseOffering;
    @ManyToOne
    private Timeslot timeslot;
    private Date date;

    public Session() {}

    public long getId() {
        return id;
    }

    private void setId(long id) {
        this.id = id;
    }

    public CourseOffering getCourseOffering() {
        return courseOffering;
    }

    public void setCourseOffering(CourseOffering courseOffering) {
        this.courseOffering = courseOffering;
    }

    public Timeslot getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(Timeslot timeslot) {
        this.timeslot = timeslot;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
