package session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import entity.Relation;
import entity.User;
import java.util.List;
import javax.persistence.Query;

@Stateless
public class RelationFacade implements RelationFacadeLocal {

    @PersistenceContext(unitName = "Class-App-ejbPU")
    private EntityManager em;

    public RelationFacade() {
    }

    private void create(Relation entity) {
        em.persist(entity);
    }

    private void edit(Relation entity) {
        em.merge(entity);
    }

    private void remove(Relation entity) {
        em.remove(em.merge(entity));
    }

    @Override
    public boolean addRelation(Relation r) {
        Boolean exists = em.contains(r);

        if (exists) {
            return false;
        }

        create(r);

        return true;
    }

    @Override
    public boolean removeRelation(String studentid, String classid) {

        Query query = em.createQuery("DELETE FROM Relation r WHERE r.studentid = '" + studentid + "' AND r.classid = '" + classid + "'");
        int count = query.executeUpdate();
        if (count == 0) {
            return false;
        } else {
            return true;
        }
    }
}
