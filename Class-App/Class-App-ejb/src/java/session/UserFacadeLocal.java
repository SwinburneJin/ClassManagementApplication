package session;

import java.util.List;
import javax.ejb.Local;
import entity.User;
import entity.Class;

@Local
public interface UserFacadeLocal {

    User find(String id);

    boolean hasUser(String userId);
    
    boolean addUser(User user);
    
    boolean updateUserDetails(User user);
    
    boolean updatePassword(String userId, String password);

    boolean deleteUser(String userId);
    
    boolean removeUser(String userId);
    
    List<Class> getClasses(String userId);

}
