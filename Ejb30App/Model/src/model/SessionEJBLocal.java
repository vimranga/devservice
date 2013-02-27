package model;

import java.util.List;

import javax.ejb.Local;

@Local
public interface SessionEJBLocal {
    Object queryByRange(String jpqlStmt, int firstResult, int maxResults);

    Emp persistEmp(Emp emp);

    Emp mergeEmp(Emp emp);

    void removeEmp(Emp emp);

    List<Emp> getEmpFindAll();

    Dept persistDept(Dept dept);

    Dept mergeDept(Dept dept);

    void removeDept(Dept dept);

    List<Dept> getDeptFindAll();
}
