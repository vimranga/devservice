package model;

import java.util.ArrayList;
 
public class DeptBean extends java.util.HashMap {    
    
public DeptBean() {
}
 
public DeptBean(int n, String d, String l, String h, ArrayList al) {
put("deptno", new Integer(n));
put("dname", d);
put("loc", l);
put("hasmanager", h);
put("emps", al);
}
 
public int getDeptno() {
  return ((Integer)get("deptno")).intValue();
}
 
public String getDname() {
  return (String) get("dname");
}
 
public String getLoc() {
  return (String) get("loc");
}
 
public String getHasmanager() {
  return (String) get("hasmanager");
}
 
public ArrayList<EmpBean> getEmps() {
  return (ArrayList) get("emps");
}
 
}
  