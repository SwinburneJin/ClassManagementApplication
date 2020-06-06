package session;

import java.util.List;
import javax.ejb.Local;
import entity.Class;
import entity.User;

@Local
public interface ClassFacadeLocal {

    Class find(String id);

    boolean hasClass(String classId);
    
    boolean addClass(Class cls);
    
    boolean updateClassDetails(Class cls);

    boolean deleteClass(String classId);
    
    boolean removeClass(String classId);
    
    List<User> getStudents(String classId);
    
    User getTeacher(String classId);

}
