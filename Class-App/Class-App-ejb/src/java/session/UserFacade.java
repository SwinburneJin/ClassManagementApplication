package session;

import entity.Class;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import entity.User;
import java.util.List;
import javax.persistence.Query;

@Stateless
public class UserFacade implements UserFacadeLocal {

    @PersistenceContext(unitName = "Class-App-ejbPU")
    private EntityManager em;

    public UserFacade() {
    }

    private void create(User entity) {
        em.persist(entity);
    }

    private void edit(User entity) {
        em.merge(entity);
    }

    private void remove(User entity) {
        em.remove(em.merge(entity));
    }

    @Override
    public User find(String id) {
        return em.find(User.class, id);
    }
    
    @Override
    public boolean hasUser(String userId) {
        return (find(userId) != null);
    }
    
    @Override
    public boolean addUser(User user) {
        User u = find(user.getUserid());
        
        if (u != null) {
            return false;
        }

        create(user);

        return true;
    }

    @Override
    public boolean updateUserDetails(User user) {
        User u = find(user.getUserid());

        if (u == null) {
            return false;
        }

        edit(user);
        return true;
    }

    @Override
    public boolean updatePassword(String userId, String password) {
        User u = find(userId);

        if (u == null) {
            return false;
        }

        u.setPassword(password);
        return true;
    }

    @Override
    public boolean deleteUser(String userId) {
        User u = find(userId);

        if (u == null) {
            return false;
        }

        if (!u.isActive()) {
            return false;
        }

        u.setActive(false);
        return true;
    }

    @Override
    public boolean removeUser(String userId) {
        User u = find(userId);

        if (u == null) {
            return false;
        }

        em.remove(u);
        return true;
    }

    @Override
    public List<Class> getClasses(String userId) {
        
        Query query = em.createQuery("SELECT c FROM Class c, Relation r WHERE c.classid = r.classid AND r.studentid = :studentId").setParameter("studentId", userId);
        List<Class> resultList = query.getResultList();
        
        return resultList;
    }
}
