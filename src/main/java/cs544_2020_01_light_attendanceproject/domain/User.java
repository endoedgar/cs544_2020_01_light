package cs544_2020_01_light_attendanceproject.domain;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotEmpty(message = "Please provide a first name.")
    private String firstName;
    @NotEmpty(message = "Please provide a last name.")
    private String lastName;
    @NotEmpty(message = "Please provide a password.")
    private String password;
    private boolean enabled;
    @Column(unique = true)
    @NotEmpty(message = "Please provide an username.")
    private String username;
    @Column(unique = true)
    @NotEmpty(message = "Please provide a bar code.")
    private String barCodeId;
    @NotEmpty(message = "Please provide an email.")
    private String email;
    @ManyToMany
    @NotEmpty(message = "Please at least one role.")
    private Set<Role> roles;
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

    public Set<Role> getRoles() {
        return Collections.unmodifiableSet(roles);
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public List<Attendance> getAttendances() {
        return Collections.unmodifiableList(attendances);
    }

    public void setAttendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }
}
