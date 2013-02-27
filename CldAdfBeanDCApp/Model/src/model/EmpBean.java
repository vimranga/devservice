package model;

public class EmpBean extends java.util.HashMap {
 
public EmpBean() {
}
 
public EmpBean(int n, String e, String l, int d) {
put("empno", new Integer(n));
put("ename", e);
put("job", l);
put("deptno", new Integer(d));
}
 
public int getEmpno() {
    return ((Integer) get("empno")).intValue();
}
 
public String getEname() {
    return (String) get("ename");
}
 
public String getJob() {
    return (String)get("job");
}
 
public int getDeptno() {
    return ((Integer) get("deptno")).intValue();
}
 
}