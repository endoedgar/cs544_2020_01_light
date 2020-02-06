package cs544_2020_01_light_attendanceproject.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(SummaryView.class)
    private long id;
    @ManyToOne
    @JsonIgnoreProperties("attendances")
    @JoinColumn(name = "barCodeId", referencedColumnName = "barCodeId")
    @JsonView(SummaryView.class)
    private User user;
    @ManyToOne
    @JsonIgnoreProperties("attendance")
    @JsonView(SummaryView.class)
    private Session session;
    @ManyToOne
    @JsonIgnoreProperties("location")
    @JsonView(SummaryView.class)
    private Location location;

    public Attendance() {}

    public long getId() {
        return id;
    }

    private void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
    public void removeuser(User user) {
        this.user = null;
    }

    public interface SummaryView extends User.SummaryView, Session.SummaryView, Location.SummaryView  {}
}
