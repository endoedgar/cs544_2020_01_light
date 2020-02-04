package cs544_2020_01_light_attendanceproject.domain;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

@Entity
public class CourseOffering {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Course course;
    @Temporal(TemporalType.TIMESTAMP)
    @NotEmpty(message = "please specify startDate")
    private Date startDate;
    @Temporal(TemporalType.TIMESTAMP)
    @NotEmpty(message = "please specify endDate")
    private Date endDate;
    @OneToMany(mappedBy = "courseOffering",cascade = CascadeType.ALL)
    @Valid
    private List<Session> session;


    public CourseOffering() {}

    public long getId() {
        return id;
    }

    private void setId(long id) {
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

    public List<Session> getSession() {
        return session;
    }

    public void setSession(List<Session> session) {
        this.session = session;
    }

}
