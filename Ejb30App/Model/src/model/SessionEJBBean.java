package model;

import java.util.List;

import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless(name = "SessionEJB", mappedName = "Ejb30App-Model-SessionEJB")
public class SessionEJBBean implements SessionEJB, SessionEJBLocal {
    @PersistenceContext(unitName="Model")
    private EntityManager em;

    public SessionEJBBean() {
    }

    public Object queryByRange(String jpqlStmt, int firstResult,
                               int maxResults) {
        Query query = em.createQuery(jpqlStmt);
        if (firstResult > 0) {
            query = query.setFirstResult(firstResult);
        }
        if (maxResults > 0) {
            query = query.setMaxResults(maxResults);
        }
        return query.getResultList();
    }

    public Emp persistEmp(Emp emp) {
        em.persist(emp);
        return emp;
    }

    public Emp mergeEmp(Emp emp) {
        return em.merge(emp);
    }

    public void removeEmp(Emp emp) {
        emp = em.find(Emp.class, emp.getEmpno());
        em.remove(emp);
    }

    /** <code>select o from Emp o</code> */
    public List<Emp> getEmpFindAll() {
        return em.createNamedQuery("Emp.findAll").getResultList();
    }

    public Dept persistDept(Dept dept) {
        em.persist(dept);
        return dept;
    }

    public Dept mergeDept(Dept dept) {
        return em.merge(dept);
    }

    public void removeDept(Dept dept) {
        dept = em.find(Dept.class, dept.getDeptno());
        em.remove(dept);
    }

    /** <code>select o from Dept o</code> */
    public List<Dept> getDeptFindAll() {
        return em.createNamedQuery("Dept.findAll").getResultList();
    }
}
