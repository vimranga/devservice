package qa.test.tl.model;

import java.util.List;

import javax.ejb.Local;

@Local
public interface SessionEJBLocal {
    Object mergeEntity(Object entity);

    Object persistEntity(Object entity);

    Object refreshEntity(Object entity);

    void removeEntity(Object entity);

    List<Emp> findAllEmp();

    List<Dept> findAllDept();
}
