package cs544_2020_01_light_attendanceproject.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

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
}
