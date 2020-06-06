package secure;

import java.io.Serializable;

public class Relation implements Serializable {

    private final String studentid;
    private final String classid;

    public Relation(String studentid, String classid) {
        this.studentid = studentid;
        this.classid = classid;
    }

    public String getStudentid() {
        return studentid;
    }

    public String getClassid() {
        return classid;
    }
    
}
