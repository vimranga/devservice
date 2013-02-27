package model;

import java.util.Collection;
import java.util.ArrayList;

public class SampleBean {
    private ArrayList<DeptBean> depts;

    public SampleBean() {
    }

    public Collection<DeptBean> getDepts() {
        if (depts == null) {
            ArrayList emp1 = new ArrayList();
            emp1.add(new EmpBean(1, "A", "Pres", 1));
            emp1.add(new EmpBean(2, "B", "Mgr", 1));
            emp1.add(new EmpBean(3, "C", "PM", 1));

            ArrayList emp2 = new ArrayList();
            emp2.add(new EmpBean(4, "AA", "Dev", 2));
            emp2.add(new EmpBean(5, "BB", "Dev", 2));
            emp2.add(new EmpBean(6, "CC", "Dev", 2));

            ArrayList emp3 = new ArrayList();

            depts = new ArrayList();
            depts.add(new DeptBean(1, "one", "loc1", "", emp1));
            depts.add(new DeptBean(2, "two", "loc2", null, emp2));
            depts.add(new DeptBean(3, "three", "loc3", "x", emp3));
            depts.add(new DeptBean(4, "four", "loc4", "x", null));
        }
        return depts;
    }
 
}

