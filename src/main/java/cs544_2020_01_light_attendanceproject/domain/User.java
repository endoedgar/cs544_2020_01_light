package cs544_2020_01_light_attendanceproject.domain;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    private String password;
    private boolean enabled;
    @NaturalId
    private String username;
    @NaturalId
    private String barCodeId;
    private String email;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> role;
    @OneToMany(mappedBy = "user")
    private List<Attendance> attendances;

    public User() {}

    public long getId() {
        return id;
    }

    private void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBarCodeId() {
        return barCodeId;
    }

    public void setBarCodeId(String barCodeId) {
        this.barCodeId = barCodeId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Role> getRole() {
        return Collections.unmodifiableList(role);
    }

    public void setRole(List<Role> role) {
        this.role = role;
    }

    public List<Attendance> getAttendances() {
        return Collections.unmodifiableList(attendances);
    }

    public void setAttendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }
}
