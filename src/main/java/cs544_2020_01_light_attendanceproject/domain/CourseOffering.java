package cs544_2020_01_light_attendanceproject.domain;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class CourseOffering {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull(message = "please specify a course")
    private Course course;
    @Temporal(TemporalType.DATE)
    @NotNull(message = "please specify startDate")
    private Date startDate;
    @Temporal(TemporalType.DATE)
    @NotNull(message = "please specify endDate")
    private Date endDate;
    @OneToMany(mappedBy = "courseOffering",cascade = CascadeType.ALL)
    @Valid
    private List<Session> sessions;


    public CourseOffering() {}

    public CourseOffering(Long id, Course course, Date startDate, Date endDate, List<Session> sessions) {
        this.id = id;
        this.course = course;
        this.startDate = startDate;
        this.endDate = endDate;
        this.sessions = sessions;
    }

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseOffering that = (CourseOffering) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(course, that.course) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(sessions, that.sessions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, course, startDate, endDate, sessions);
    }
}
