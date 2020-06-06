package entity;

import java.io.Serializable;

public class RelationDTO implements Serializable {
    
    String studentid;
    String classid;
    
    public RelationDTO(String studentid, String classid) {
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
