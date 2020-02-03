package cs544_2020_01_light_attendanceproject.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.sql.Time;
import java.util.Date;

@Entity
public class Timeslot {
    @Id
    private String abbreviation;
    private String description;
    @Temporal(TemporalType.TIME)
    private Date beginTime;
    @Temporal(TemporalType.TIME)
    private Date endTime;

    public Timeslot() {
    }

    public Timeslot(String abbreviation, String description, Date beginTime, Date endTime) {
        this.abbreviation = abbreviation;
        this.description = description;
        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    private void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
