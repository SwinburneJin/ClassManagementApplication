/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import session.ClassManagementRemote;
import entity.ClassDTO;
import entity.UserDTO;
import java.sql.Time;
import java.util.ArrayList;

/**
 *
 * @author elau
 */
@Named(value = "myClassManagedBean")
@ConversationScoped
public class MyClassManagedBean implements Serializable {

    @Inject
    private Conversation conversation;
    @EJB
    private ClassManagementRemote classManagement;

    private String classId;
    private String className;
    private int startHH;
    private int startMM;
    private Time startTime;
    private int durationHH;
    private int durationMM;
    private Time duration;
    private Boolean active;
    private String teacherId;
    private ArrayList<UserDTO> students;
    private UserDTO teacher;

    /**
     * Creates a new instance of MyClassManagedBean
     */
    public MyClassManagedBean() {
        classId = null;
        className = null;
        startHH = 0;
        startMM = 0;
        startTime = null;
        durationHH = 0;
        durationMM = 0;
        duration = null;
        active = false;
        teacherId = null;
        students = null;
        teacher = null;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public ClassManagementRemote getClassManagement() {
        return classManagement;
    }

    public void setClassManagement(ClassManagementRemote classManagement) {
        this.classManagement = classManagement;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getStartHH() {
        return startHH;
    }

    public void setStartHH(int startHH) {
        this.startHH = startHH;
    }

    public int getStartMM() {
        return startMM;
    }

    public void setStartMM(int startMM) {
        this.startMM = startMM;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public int getDurationHH() {
        return durationHH;
    }

    public void setDurationHH(int durationHH) {
        this.durationHH = durationHH;
    }

    public int getDurationMM() {
        return durationMM;
    }

    public void setDurationMM(int durationMM) {
        this.durationMM = durationMM;
    }

    public Time getDuration() {
        return duration;
    }

    public void setDuration(Time duration) {
        this.duration = duration;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public Boolean isActive() {
        return active;
    }

    public void startConversation() {
        conversation.begin();
    }

    public void endConversation() {
        conversation.end();
    }

    public ArrayList<UserDTO> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<UserDTO> students) {
        this.students = students;
    }

    public UserDTO getTeacher() {
        return teacher;
    }

    public void setTeacher(UserDTO teacher) {
        this.teacher = teacher;
    }

    public String addClass() {
        // check classId is null
        if (isNull(classId)) {
            return "debug";
        }

        // all information seems to be valid
        // try add the class
        startTime = new Time(startHH, startMM, 0);
        duration = new Time(durationHH, durationMM, 0);

        ClassDTO classDTO = new ClassDTO(classId, className, startTime, duration, active, teacherId);
        boolean result = classManagement.addClass(classDTO);
        if (result) {
            return "success";
        } else {
            return "failure";
        }

    }

    public String setClassDetailsForChange() {
        // check classId is null
        if (isNull(classId) || conversation == null) {
            return "debug";
        }

        if (!classManagement.hasClass(classId)) {
            return "failure";
        }

        // note the startConversation of the conversation
        startConversation();

        // get class details
        return setClassDetails();
    }

    public String changeClass() {
        // check classId is null
        if (isNull(classId)) {
            return "debug";
        }

        startTime = new Time(startHH, startMM, 0);
        duration = new Time(durationHH, durationMM, 0);

        ClassDTO classDTO = new ClassDTO(classId, className, startTime, duration, active, teacherId);
        boolean result = classManagement.updateClassDetails(classDTO);

        // note the endConversation of the conversation
        endConversation();

        if (result) {
            return "success";
        } else {
            return "failure";
        }
    }

    public String deleteClass() {
        // check classId is null
        if (isNull(classId)) {
            return "debug";
        }

        boolean result = classManagement.deleteClass(classId);

        if (result) {
            return "success";
        } else {
            return "failure";
        }

    }

    public String displayClass() {
        // check classId is null
        if (isNull(classId) || conversation == null) {
            return "debug";
        }

        return setClassDetails();
    }

    public String displayClassById(String classId) {
        // check classId is null
        if (isNull(classId) || conversation == null) {
            return "debug";
        }

        this.classId = classId;

        return setClassDetails();
    }

    private boolean isNull(String s) {
        return (s == null);
    }

    private String setClassDetails() {

        if (isNull(classId) || conversation == null) {
            return "debug";
        }

        ClassDTO e = classManagement.getClassDetails(classId);

        if (e == null) {
            // no such class
            return "failure";
        } else {
            // found - set details for display
            this.classId = e.getClassid();
            this.className = e.getClassName();
            this.startTime = e.getStartTime();
            this.startHH = e.getStartTime().getHours();
            this.startMM = e.getStartTime().getMinutes();
            this.duration = e.getDuration();
            this.durationHH = e.getDuration().getHours();
            this.durationMM = e.getDuration().getMinutes();
            this.active = e.isActive();
            this.teacherId = e.getTeacherId();
            this.students = classManagement.getStudents(classId);
            this.teacher = classManagement.getTeacher(classId);
            return "success";
        }
    }

}
