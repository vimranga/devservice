package qa.test.tl.model;

import java.util.List;

import javax.ejb.Stateless;

import org.eclipse.persistence.sessions.Session;
import org.eclipse.persistence.sessions.UnitOfWork;
import org.eclipse.persistence.sessions.factories.SessionFactory;

@Stateless(name = "SessionEJB", mappedName = "TopLinkApp-Model-SessionEJB")
public class SessionEJBBean implements SessionEJBLocal {
    private SessionFactory sessionFactory;

    public SessionEJBBean() {
        this.sessionFactory = new SessionFactory("META-INF/sessions.xml", "default");
    }

    public Object mergeEntity(Object entity) {
        UnitOfWork uow = getSessionFactory().acquireUnitOfWork();
        Object workingCopy = uow.readObject(entity);
        if (workingCopy == null)
            throw new RuntimeException("Could not find entity to update");
        uow.deepMergeClone(entity);
        uow.commit();

        return workingCopy;
    }

    public Object persistEntity(Object entity) {
        UnitOfWork uow = getSessionFactory().acquireUnitOfWork();
        Object newInstance = uow.registerNewObject(entity);
        uow.commit();

        return newInstance;
    }

    public Object refreshEntity(Object entity) {
        Session session = getSessionFactory().acquireSession();
        Object refreshedEntity = session.refreshObject(entity);
        session.release();

        return refreshedEntity;
    }

    public void removeEntity(Object entity) {
        UnitOfWork uow = getSessionFactory().acquireUnitOfWork();
        Object workingCopy = uow.readObject(entity);
        if (workingCopy == null)
            throw new RuntimeException("Could not find entity to update");
        uow.deleteObject(workingCopy);
        uow.commit();
    }

    private SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }

    public List<Emp> findAllEmp() {
        Session session = getSessionFactory().acquireSession();
        List<Emp> result =
            (List<Emp>)session.executeQuery("findAllEmp", Emp.class);
        session.release();
        // Uncomment the following lines of code if the results from this query will be mutated.
        // See SessionFactory.detach() for more information.
        // result = (java.util.List<qa.test.tl.model.Emp>)getSessionFactory().detach(result);

        return result;
    }

    public List<Dept> findAllDept() {
        Session session = getSessionFactory().acquireSession();
        List<Dept> result =
            (List<Dept>)session.executeQuery("findAllDept", Dept.class);
        session.release();
        // Uncomment the following lines of code if the results from this query will be mutated.
        // See SessionFactory.detach() for more information.
        // result = (java.util.List<qa.test.tl.model.Dept>)getSessionFactory().detach(result);

        return result;
    }
}
