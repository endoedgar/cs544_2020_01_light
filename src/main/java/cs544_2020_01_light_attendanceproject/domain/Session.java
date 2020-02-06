package cs544_2020_01_light_attendanceproject.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne//(cascade = CascadeType.ALL)
    private CourseOffering courseOffering;
    @ManyToOne
    private Timeslot timeslot;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone="CST")
    @Temporal(TemporalType.DATE)
    private Date date;
    @OneToMany(mappedBy = "session", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("session")
    private List<Attendance> attendances;

    public Session() {}

    public Session(Long id, CourseOffering courseOffering, Timeslot timeslot, Date date) {
        this.id = id;
        this.courseOffering = courseOffering;
        this.timeslot = timeslot;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(id, session.id) &&
                Objects.equals(courseOffering, session.courseOffering) &&
                Objects.equals(timeslot, session.timeslot) &&
                Objects.equals(date, session.date);
    }

    public List<Attendance> getAttendances() {
        return attendances;
    }

    public void setAttendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, courseOffering, timeslot, date);
    }
}
