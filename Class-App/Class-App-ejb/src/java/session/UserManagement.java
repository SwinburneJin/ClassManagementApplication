package session;

import entity.ClassDTO;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import entity.User;
import entity.Class;
import entity.UserDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;

@DeclareRoles({"CMA_TEACHER", "CMA_STUDENT"})
@Stateless
public class UserManagement implements UserManagementRemote {

    @EJB
    private UserFacadeLocal userFacade;

    private User userDTO2Entity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }

        String userid = userDTO.getUserid();
        String name = userDTO.getName();
        String phone = userDTO.getPhone();
        String address = userDTO.getAddress();
        String email = userDTO.getEmail();
        String password = userDTO.getPassword();
        String userGroup = userDTO.getUserGroup();
        Boolean active = userDTO.isActive();

        User user = new User(userid, name, phone, address, email,
                password, userGroup, active);

        return user;
    }

    private UserDTO userEntity2DTO(User user) {
        if (user == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO(
                user.getUserid(),
                user.getName(),
                user.getPhone(),
                user.getAddress(),
                user.getEmail(),
                user.getPassword(),
                user.getUserGroup(),
                user.isActive()
        );

        return userDTO;
    }

    @Override
    @RolesAllowed({"CMA_TEACHER", "CMA_STUDENT"})
    public boolean hasUser(String userId) {
        return userFacade.hasUser(userId);
    }

    @Override
    @RolesAllowed({"CMA_TEACHER"})
    public boolean addUser(UserDTO userDTO) {

        if (userDTO == null) {
            return false;
        }

        if (hasUser(userDTO.getUserid())) {
            return false;
        }

        User user = this.userDTO2Entity(userDTO);
        return userFacade.addUser(user);
    }

    @Override
    @RolesAllowed({"CMA_TEACHER", "CMA_STUDENT"})
    public boolean updateUserDetails(UserDTO userDTO) {
        if (!hasUser(userDTO.getUserid())) {
            return false;
        }

        User user = this.userDTO2Entity(userDTO);
        return userFacade.updateUserDetails(user);
    }

    @Override
    @RolesAllowed({"CMA_TEACHER", "CMA_STUDENT"})
    public boolean updateUserPassword(String userId, String newPassword) {
        return userFacade.updatePassword(userId, newPassword);
    }

    @Override
    public UserDTO getUserDetails(String userId) {
        User user = userFacade.find(userId);

        if (user == null) {
            return null;
        } else {
            UserDTO userDTO = new UserDTO(user.getUserid(),
                    user.getName(), user.getPhone(), user.getAddress(),
                    user.getEmail(), user.getPassword(), user.getUserGroup(),
                    user.isActive());

            return userDTO;
        }
    }

    @Override
    @RolesAllowed({"CMA_TEACHER"})
    public boolean deleteUser(String userId) {
        return userFacade.deleteUser(userId);
    }

    @Override
    @RolesAllowed({"CMA_TEACHER"})
    public boolean removeUser(String userId) {
        return userFacade.removeUser(userId);
    }

    @Override
    @RolesAllowed({"CMA_TEACHER", "CMA_STUDENT"})
    public ArrayList<ClassDTO> getClasses(String userid) {
        List<Class> classes = userFacade.getClasses(userid);
        ArrayList<ClassDTO> classesDTO = new ArrayList<>();

        for (Class c : classes) {
            classesDTO.add(
                    new ClassDTO(
                            c.getClassid(),
                            c.getClassname(),
                            c.getStarttime(),
                            c.getDuration(),
                            c.isActive(),
                            c.getTeacherid()
                    )
            );
        }

        return classesDTO;
    }

}
