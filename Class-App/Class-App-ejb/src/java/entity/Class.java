package entity;

import java.io.Serializable;
import java.sql.Time;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "CMA_CLASS", catalog = "", schema = "APP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Class.findAll", query = "SELECT c FROM Class c"),
    @NamedQuery(name = "Class.findByClassId", query = "SELECT c FROM Class c WHERE c.classid = :classid"),
    @NamedQuery(name = "Class.findByClassName", query = "SELECT c FROM Class c WHERE c.classname = :classname"),
    @NamedQuery(name = "Class.findByStartTime", query = "SELECT c FROM Class c WHERE c.starttime = :starttime"),
    @NamedQuery(name = "Class.findByDuration", query = "SELECT c FROM Class c WHERE c.duration = :duration"),
    @NamedQuery(name = "Class.findByActive", query = "SELECT c FROM Class c WHERE c.active = :active"),
    @NamedQuery(name = "Class.findByTeacherId", query = "SELECT c FROM Class c WHERE c.teacherid = :teacherid")})
public class Class implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CLASSID")
    private String classid;
    @Column(name = "CLASSNAME")
    private String classname;
    @Column(name = "STARTTIME")
    private Time starttime;
    @Column(name = "DURATION")
    private Time duration;
    @Column(name = "ACTIVE")
    private Boolean active;
    @Column(name = "TEACHERID")
    private String teacherid;

    public Class() {
    }

    public Class(String classid) {
        this.classid = classid;
    }

    public Class(String classid, String classname, Time starttime, Time duration, Boolean active, String teacherid) {
        this.classid = classid;
        this.classname = classname;
        this.starttime = starttime;
        this.duration = duration;
        this.active = active;
        this.teacherid = teacherid;
    }

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public Time getStarttime() {
        return starttime;
    }

    public void setStarttime(Time starttime) {
        this.starttime = starttime;
    }

    public Time getDuration() {
        return duration;
    }

    public void setDuration(Time duration) {
        this.duration = duration;
    }

    public String getTeacherid() {
        return teacherid;
    }

    public void setTeacherid(String teacherid) {
        this.teacherid = teacherid;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (classid != null ? classid.hashCode() : 0);
        return hash;
    }


    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Class)) {
            return false;
        }
        Class other = (Class) object;
        if ((this.classid == null && other.classid != null) || (this.classid != null && !this.classid.equals(other.classid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistence.entity.Class[ classid=" + classid + " ]";
    }
}
