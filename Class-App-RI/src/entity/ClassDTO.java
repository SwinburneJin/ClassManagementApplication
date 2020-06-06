package entity;

import java.io.Serializable;
import java.sql.Time;

public class ClassDTO implements Serializable {
    
    String classid;
    String className;
    Time startTime;
    Time duration;
    Boolean active;
    String teacherId;
    
    public ClassDTO(String classid, String className, Time startTime, Time duration,
            Boolean active, String teacherId) {
        this.classid = classid;
        this.className = className;
        this.startTime = startTime;
        this.duration = duration;
        this.active = active;
        this.teacherId = teacherId;
    }

    public String getClassid() {
        return classid;
    }

    public String getClassName() {
        return className;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getDuration() {
        return duration;
    }

    public Boolean isActive() {
        return active;
    }

    public String getTeacherId() {
        return teacherId;
    }

    
}
