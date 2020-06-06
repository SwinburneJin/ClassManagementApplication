package session;

import javax.ejb.Local;
import entity.Relation;

@Local
public interface RelationFacadeLocal {
    
    boolean addRelation(Relation r);
    
    boolean removeRelation(String studentid, String classid);

}
