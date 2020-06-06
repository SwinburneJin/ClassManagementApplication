package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "CMA_STUDENT_CLASS_RELATION", catalog = "", schema = "APP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Relation.findAll", query = "SELECT r FROM Relation r"),
    @NamedQuery(name = "Relation.findByStudentid", query = "SELECT r FROM Relation r WHERE r.studentid = :studentid"),
    @NamedQuery(name = "Relation.findByClassid", query = "SELECT r FROM Relation r WHERE r.classid = :classid")})
public class Relation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "STUDENTID")
    private String studentid;
    @Column(name = "CLASSID")
    private String classid;
    
    public Relation() {
    }

    public Relation(String studentid, String classid) {
        this.studentid = studentid;
        this.classid = classid;
    }

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (studentid != null ? studentid.hashCode() : 0);
        hash += (classid != null ? classid.hashCode() : 0);
        return hash;
    }


    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Relation)) {
            return false;
        }
        Relation other = (Relation) object;
        if ((this.studentid == null && other.studentid != null) || (this.classid == null && other.classid != null) || (this.studentid != null && !this.studentid.equals(other.studentid)) || (this.classid != null && !this.classid.equals(other.classid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistence.entity.Relation[ studentid=" + studentid + ", classid=" + classid + " ]";
    }
}
