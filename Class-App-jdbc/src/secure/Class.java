package secure;

import java.io.Serializable;

public class Class implements Serializable {

    private final String classId;
    private final String className;
    private final String startTime;
    private final String duration;
    private final boolean active;
    private final String teacherId;

    public Class(String classId, String className, String startTime, String duration,
            boolean active, String teacherId) {
        this.classId = classId;
        this.className = className;
        this.startTime = startTime;
        this.duration = duration;
        this.active = active;
        this.teacherId = teacherId;
    }

    public String getClassId() {
        return classId;
    }

    public String getClassName() {
        return className;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getDuration() {
        return duration;
    }

    public boolean isActive() {
        return active;
    }

    public String getTeacherId() {
        return teacherId;
    }
    

}
