package cs544_2020_01_light_attendanceproject.domain;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(SummaryView.class)
    private Long id;
    @NotEmpty(message = "Please provide a first name.")
    @JsonView(SummaryView.class)
    private String firstName;
    @NotEmpty(message = "Please provide a last name.")
    @JsonView(SummaryView.class)
    private String lastName;
    @NotEmpty(message = "Please provide a password.")
    @JsonView(SummaryView.class)
    private String password;
    @JsonView(SummaryView.class)
    private boolean enabled;
    @Column(unique = true)
    @NotEmpty(message = "Please provide an username.")
    @JsonView(SummaryView.class)
    private String username;
    @Column(unique = true)
    @Pattern(regexp = "^\\d{3}-[a-zA-z]{2}-[a-zA-Z]{4}$", message = "Please provide a valid bar code (pattern 000-xx-yyyy)")
    @NotEmpty(message = "Please provide a bar code.")
    @JsonView(SummaryView.class)
    private String barCodeId;
    @NotEmpty(message = "Please provide an email.")
    @Email(message = "Please provide a valid email.")
    @JsonView(SummaryView.class)
    private String email;
    @ManyToMany
    @NotEmpty(message = "Please provide at least one role.")
    @JsonView(SummaryView.class)
    @JoinTable
    private Set<Role> roles;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("user")
    @JsonView(DetailView.class)
    private List<Attendance> attendances;
    @ManyToMany(fetch = FetchType.LAZY)
    @JsonView(DetailView.class)
    @JsonIgnoreProperties("user")
    @JoinTable(
            name = "user_course_offerings",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "course_offerings_id") }
    )
    private List<CourseOffering> courseOfferings;

    public User() {}

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
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

    public List<CourseOffering> getCourseOfferings() {
        return courseOfferings;
    }

    public void setCourseOfferings(List<CourseOffering> courseOfferings) {
        this.courseOfferings = courseOfferings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return enabled == user.enabled &&
                Objects.equals(id, user.id) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(password, user.password) &&
                Objects.equals(username, user.username) &&
                Objects.equals(barCodeId, user.barCodeId) &&
                Objects.equals(email, user.email) &&
                Objects.equals(roles, user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, password, enabled, username, barCodeId, email, roles);
    }

    public void removeCourseOffering(CourseOffering courseOffering) {
        this.courseOfferings.remove(courseOffering);
    }
    @PreRemove
    private void removeAssociationsWithStudentsCourseOffering() {
        for (CourseOffering u : this.getCourseOfferings() ){
            u.removeuser(this);
        }
        for (Attendance u : this.getAttendances() ){
            u.removeuser(this);
        }

        for(Role r : this.getRoles()){
            r.removeuser(this);
        }
    }

    public interface SummaryView extends Role.SummaryView {}
    public interface DetailView extends SummaryView, Attendance.SummaryView, CourseOffering.SummaryView {}
}
