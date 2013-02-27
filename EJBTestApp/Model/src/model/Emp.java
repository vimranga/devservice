package model;

import java.io.Serializable;

import java.sql.Timestamp;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({
  @NamedQuery(name = "Emp.findAll", query = "select o from Emp o")
})
public class Emp implements Serializable {
    private Double comm;
    @Id
    @Column(nullable = false)
    private Long empno;
    @Column(length = 10)
    private String ename;
    private Timestamp hiredate;
    @Column(length = 9)
    private String job;
    private Double sal;
    @ManyToOne
    @JoinColumn(name = "DEPTNO")
    private Dept dept;
    @ManyToOne
    @JoinColumn(name = "MGR")
    private Emp emp;
    @OneToMany(mappedBy = "emp")
    private List<Emp> empList;

    public Emp() {
    }

    public Emp(Double comm, Dept dept, Long empno, String ename,
               Timestamp hiredate, String job, Emp emp, Double sal) {
        this.comm = comm;
        this.dept = dept;
        this.empno = empno;
        this.ename = ename;
        this.hiredate = hiredate;
        this.job = job;
        this.emp = emp;
        this.sal = sal;
    }

    public Double getComm() {
        return comm;
    }

    public void setComm(Double comm) {
        this.comm = comm;
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


    public Double getSal() {
        return sal;
    }

    public void setSal(Double sal) {
        this.sal = sal;
    }

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    public Emp getEmp() {
        return emp;
    }

    public void setEmp(Emp emp) {
        this.emp = emp;
    }

    public List<Emp> getEmpList() {
        return empList;
    }

    public void setEmpList(List<Emp> empList) {
        this.empList = empList;
    }

    public Emp addEmp(Emp emp) {
        getEmpList().add(emp);
        emp.setEmp(this);
        return emp;
    }

    public Emp removeEmp(Emp emp) {
        getEmpList().remove(emp);
        emp.setEmp(null);
        return emp;
    }
}
