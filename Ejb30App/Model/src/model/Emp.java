package model;

import java.io.Serializable;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
  @NamedQuery(name = "Emp.findAll", query = "select o from Emp o")
})
public class Emp implements Serializable {
    private Double comm;
    private Long deptno;
    @Column(nullable = false)
    @Id
    private Long empno;
    @Column(length = 10)
    private String ename;
    private Timestamp hiredate;
    @Column(length = 9)
    private String job;
    private Long mgr;
    private Double sal;

    public Emp() {
    }

    public Emp(Double comm, Long deptno, Long empno, String ename,
               Timestamp hiredate, String job, Long mgr, Double sal) {
        this.comm = comm;
        this.deptno = deptno;
        this.empno = empno;
        this.ename = ename;
        this.hiredate = hiredate;
        this.job = job;
        this.mgr = mgr;
        this.sal = sal;
    }

    public Double getComm() {
        return comm;
    }

    public void setComm(Double comm) {
        this.comm = comm;
    }

    public Long getDeptno() {
        return deptno;
    }

    public void setDeptno(Long deptno) {
        this.deptno = deptno;
    }

    public Long getEmpno() {
        return empno;
    }

    public void setEmpno(Long empno) {
        this.empno = empno;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public Timestamp getHiredate() {
        return hiredate;
    }

    public void setHiredate(Timestamp hiredate) {
        this.hiredate = hiredate;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Long getMgr() {
        return mgr;
    }

    public void setMgr(Long mgr) {
        this.mgr = mgr;
    }

    public Double getSal() {
        return sal;
    }

    public void setSal(Double sal) {
        this.sal = sal;
    }
}
