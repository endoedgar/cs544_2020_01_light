package cs544_2020_01_light_attendanceproject.domain;

import javax.persistence.*;

@Entity
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "barCodeId", referencedColumnName = "barCodeId")
    private User user;
    @ManyToOne
    private Session session;
    @ManyToOne
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
}
