package session;

import javax.ejb.Remote;
import entity.RelationDTO;

@Remote
public interface RelationManagementRemote {
    
    boolean addRelation(RelationDTO relationDTO);
    
    boolean removeRelation(String studentid, String classid);
}
