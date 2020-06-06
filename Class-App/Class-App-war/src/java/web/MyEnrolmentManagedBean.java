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
import session.RelationManagementRemote;
import entity.RelationDTO;

/**
 *
 * @author elau
 */
@Named(value = "myEnrolmentManagedBean")
@ConversationScoped
public class MyEnrolmentManagedBean implements Serializable {

    @Inject
    private Conversation conversation;
    @EJB
    private RelationManagementRemote relationManagement;

    private String studentId;
    private String classId;

    /**
     * Creates a new instance of MyEnrolmentManagedBean
     */
    public MyEnrolmentManagedBean() {
        studentId = null;
        classId = null;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public RelationManagementRemote getRelationManagement() {
        return relationManagement;
    }

    public void setRelationManagement(RelationManagementRemote relationManagement) {
        this.relationManagement = relationManagement;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String addEnrolment() {
        // check classId is null
        if (isNull(classId) || isNull(studentId)) {
            return "debug";
        }

        RelationDTO relationDTO = new RelationDTO(studentId, classId);
        boolean result = relationManagement.addRelation(relationDTO);
        if (result) {
            return "success";
        } else {
            return "failure";
        }

    }

    public String deleteEnrolment() {
        // check classId is null
        if (isNull(classId) || isNull(studentId)) {
            return "debug";
        }

        boolean result = relationManagement.removeRelation(studentId, classId);

        if (result) {
            return "success";
        } else {
            return "failure";
        }

    }

    private boolean isNull(String s) {
        return (s == null);
    }

}
