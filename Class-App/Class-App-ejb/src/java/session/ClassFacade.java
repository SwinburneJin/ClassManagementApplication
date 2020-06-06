package session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import entity.Class;
import entity.User;
import java.util.List;
import javax.persistence.Query;

@Stateless
public class ClassFacade implements ClassFacadeLocal {

    @PersistenceContext(unitName = "Class-App-ejbPU")
    private EntityManager em;

    public ClassFacade() {
    }

    private void create(Class entity) {
        em.persist(entity);
    }

    private void edit(Class entity) {
        em.merge(entity);
    }

    private void remove(Class entity) {
        em.remove(em.merge(entity));
    }

    @Override
    public Class find(String id) {
        return em.find(Class.class, id);
    }
    
    @Override
    public boolean hasClass(String classId) {
        return (find(classId) != null);
    }
    
    @Override
    public boolean addClass(Class cls) {
        Class c = find(cls.getClassid());
        
        if (c != null) {
            return false;
        }

        create(cls);

        return true;
    }

    @Override
    public boolean updateClassDetails(Class cls) {
        Class c = find(cls.getClassid());

        if (c == null) {
            return false;
        }

        edit(cls);
        return true;
    }

    @Override
    public boolean deleteClass(String classId) {
        Class c = find(classId);

        if (c == null) {
            return false;
        }

        if (!c.isActive()) {
            return false;
        }

        c.setActive(false);
        return true;
    }

    @Override
    public boolean removeClass(String classId) {
        Class c = find(classId);

        if (c == null) {
            return false;
        }

        em.remove(c);
        return true;
    }
    
    @Override
    public List<User> getStudents(String classId) {
        Query query = em.createQuery("SELECT u FROM User u, Relation r WHERE u.userid = r.studentid AND r.classid = :classId").setParameter("classId", classId);
        List<User> resultList = query.getResultList();
        
        return resultList;
    }
    
    @Override
    public User getTeacher(String classId) {
        Query query = em.createQuery("SELECT u FROM Class c, User u WHERE u.userid = c.teacherid AND c.classid = :classId").setParameter("classId", classId);
        List<User> resultList = query.getResultList();
        
        return resultList.get(0);
    }
}
