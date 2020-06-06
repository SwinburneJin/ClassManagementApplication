package session;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import entity.Class;
import entity.ClassDTO;
import entity.User;
import entity.UserDTO;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;

@DeclareRoles({"CMA_TEACHER", "CMA_STUDENT"})
@Stateless
public class ClassManagement implements ClassManagementRemote {

    @EJB
    private ClassFacadeLocal classFacade;

    private Class classDTO2Entity(ClassDTO classDTO) {
        if (classDTO == null) {
            return null;
        }

        String classId = classDTO.getClassid();
        String className = classDTO.getClassName();
        Time startTime = classDTO.getStartTime();
        Time duration = classDTO.getDuration();
        Boolean active = classDTO.isActive();
        String teacherId = classDTO.getTeacherId();

        Class cls = new Class(classId, className, startTime, duration, active, teacherId);

        return cls;
    }

    private ClassDTO classEntity2DTO(Class cls) {
        if (cls == null) {
            return null;
        }

        ClassDTO classDTO = new ClassDTO(
                cls.getClassid(),
                cls.getClassname(),
                cls.getStarttime(),
                cls.getDuration(),
                cls.isActive(),
                cls.getTeacherid()
        );

        return classDTO;
    }

    @Override
    @RolesAllowed({"CMA_TEACHER", "CMA_STUDENT"})
    public boolean hasClass(String classId) {
        return classFacade.hasClass(classId);
    }

    @Override
    @RolesAllowed({"CMA_TEACHER"})
    public boolean addClass(ClassDTO classDTO) {

        if (classDTO == null) {
            return false;
        }

        if (hasClass(classDTO.getClassid())) {
            return false;
        }

        Class cls = this.classDTO2Entity(classDTO);
        return classFacade.addClass(cls);
    }

    @Override
    @RolesAllowed({"CMA_TEACHER", "CMA_STUDENT"})
    public boolean updateClassDetails(ClassDTO classDTO) {
        if (!hasClass(classDTO.getClassid())) {
            return false;
        }

        Class cls = this.classDTO2Entity(classDTO);
        return classFacade.updateClassDetails(cls);
    }

    @Override
    public ClassDTO getClassDetails(String classId) {
        Class cls = classFacade.find(classId);

        if (cls == null) {
            return null;
        } else {
            ClassDTO classDTO = new ClassDTO(cls.getClassid(),
                    cls.getClassname(), cls.getStarttime(), cls.getDuration(),
                    cls.isActive(), cls.getTeacherid());

            return classDTO;
        }
    }

    @Override
    @RolesAllowed({"CMA_TEACHER", "CMA_STUDENT"})
    public ArrayList<UserDTO> getStudents(String classId) {
        List<User> studentsDAO = classFacade.getStudents(classId);
        ArrayList<UserDTO> students = new ArrayList<>();

        studentsDAO.forEach((user) -> {

            students.add(
                    new UserDTO(
                            user.getUserid(),
                            user.getName(),
                            user.getPhone(),
                            user.getAddress(),
                            user.getEmail(),
                            user.getPassword(),
                            user.getUserGroup(),
                            user.isActive()
                    )
            );
        });

        return students;
    }

    @Override
    @RolesAllowed({"CMA_TEACHER", "CMA_STUDENT"})
    public UserDTO getTeacher(String classId) {
        User teacherDAO = classFacade.getTeacher(classId);
        return new UserDTO(
                teacherDAO.getUserid(),
                teacherDAO.getName(),
                teacherDAO.getPhone(),
                teacherDAO.getAddress(),
                teacherDAO.getEmail(),
                teacherDAO.getPassword(),
                teacherDAO.getUserGroup(),
                teacherDAO.isActive()
        );
    }

    @Override
    @RolesAllowed({"CMA_TEACHER"})
    public boolean deleteClass(String classId) {
        return classFacade.deleteClass(classId);
    }

    @Override
    @RolesAllowed({"CMA_TEACHER"})
    public boolean removeClass(String classId) {
        return classFacade.removeClass(classId);
    }

}
