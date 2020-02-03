package cs544_2020_01_light_attendanceproject.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Role {
    @Id
    private String type;

    public Role() {}
    public Role(String type) { this.type = type; }

    public String getType() {
        return type;
    }

    private void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(type, role.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
}
