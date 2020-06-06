package session;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import entity.Relation;
import entity.RelationDTO;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;

@DeclareRoles({"CMA_TEACHER","CMA_STUDENT"})
@Stateless
public class RelationManagement implements RelationManagementRemote {

    @EJB
    private RelationFacadeLocal relationFacade;

    private Relation relationDTO2Entity(RelationDTO relationDTO) {
        if (relationDTO == null) {
            return null;
        }

        String classId = relationDTO.getClassid();
        String studentId = relationDTO.getStudentid();

        Relation r = new Relation(classId, studentId);

        return r;
    }
    
    private RelationDTO relationEntity2DTO(Relation r) {
        if (r == null) {
            return null;
        }
        
        RelationDTO relationDTO = new RelationDTO(
                r.getClassid(),
                r.getStudentid()
        );
        
        return relationDTO;
    }

    @Override
    @RolesAllowed({"CMA_TEACHER"})
    public boolean addRelation(RelationDTO relationDTO) {
        if (relationDTO == null) {
            return false;
        }

        Relation r = this.relationDTO2Entity(relationDTO);
        return relationFacade.addRelation(r);
    }
    
    @Override
    @RolesAllowed({"CMA_TEACHER"})
    public boolean removeRelation(String studentid, String classid) {
        return relationFacade.removeRelation(studentid, classid);
    }

}
