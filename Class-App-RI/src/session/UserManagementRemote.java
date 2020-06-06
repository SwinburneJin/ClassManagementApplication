package session;

import entity.ClassDTO;
import javax.ejb.Remote;
import entity.UserDTO;
import java.util.ArrayList;

@Remote
public interface UserManagementRemote {
    
    boolean hasUser(String userid);
    
    boolean addUser(UserDTO userDTO);

    boolean updateUserDetails(UserDTO userDTO);

    boolean updateUserPassword(String userid, String newPassword);

    UserDTO getUserDetails(String userid);

    boolean deleteUser(String userid);
    
    boolean removeUser(String userid);
    
    ArrayList<ClassDTO> getClasses(String userid);
}
