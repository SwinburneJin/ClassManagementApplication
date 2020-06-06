package session;

import javax.ejb.Remote;
import entity.ClassDTO;
import java.util.ArrayList;
import entity.UserDTO;

@Remote
public interface ClassManagementRemote {

    boolean hasClass(String classid);
    
    boolean addClass(ClassDTO classDTO);

    boolean updateClassDetails(ClassDTO classDTO);

    ClassDTO getClassDetails(String classid);

    boolean deleteClass(String classid);
    
    boolean removeClass(String classid);
    
    ArrayList<UserDTO> getStudents(String classid);
    
    UserDTO getTeacher(String classid);
}
