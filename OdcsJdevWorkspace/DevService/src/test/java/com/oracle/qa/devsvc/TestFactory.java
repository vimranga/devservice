package com.oracle.qa.devsvc;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;



import org.reflections.Reflections;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;



/**
 *
 * @author ADF QA
 */
public class TestFactory {

    public static final String APP_NAMES = "APP_NAMES";

    static final Logger LOG = Logger.getLogger(TestFactory.class.getName());


   @Factory
    public Object[] createInstances() throws Exception {
        LOG.info("TestFactory Running...");

        String groups = System.getProperty("testng.groups", "");
        LOG.info("Groups: "+ groups);
        List tests = new LinkedList();

        String pkg = getClass().getPackage().getName();
        Reflections reflections = new Reflections(pkg);

        Iterator<Class<? extends TestBase>> classes =
                reflections.getSubTypesOf(TestBase.class).iterator();
        
        LOG.info("Classes found: " + reflections.getSubTypesOf(TestBase.class).size());

        while (classes.hasNext()) {
            Class<? extends TestBase> klass = classes.next();

            Test a = (Test) klass.getAnnotation(Test.class);

            String[] classGroups = a.groups();
            for (int i = 0; i < classGroups.length; i++) {
                String group = classGroups[i];
                if (groups.contains(group)) {

                    LOG.info("Adding: " + klass.getName());

                    String serviceURL = getEnv("SERVICE_URL");
                    String userName = getEnv("USER_NAME");
                    String password = getEnv("PASSWORD");
                    String nonAdminUserName = getEnv("NONADMIN_USER_NAME");
                    String nonAdminPassword = getEnv("NONADMIN_PASSWORD");
		    
                    TestBase test = (TestBase) klass.newInstance();

                    test.setServiceURL(serviceURL);
                    test.setUserName(userName);
                    test.setPassword(password);
                    test.setNonAdminUserName(nonAdminUserName);
                    test.setNonAdminPassword(nonAdminPassword);
                    
                    tests.add(test);
                }
            }
        }

     /*   if (groups.contains(AppTest.GROUP)) {

            String applications = getEnv(APP_NAMES);
            String projectURL = getEnv("PROJECT_URL");
            String userName = getEnv("USER_NAME");
            String password = getEnv("PASSWORD");
            String config = getEnv("CONFIG");

            String[] array = applications.trim().split(" ");

            for (int i = 0; i < array.length; i++) {

                AppTest test = new AppTest();

                test.setAppName(array[i]);
                test.setProjectURL(projectURL);
                test.setUserName(userName);
                test.setPassword(password);
                test.setConfigFileName(config);

                tests.add(test);
            }
        }*/

	return tests.toArray();
    }


    String getEnv(String var) {
        String value = System.getenv(var);

        if (value == null) {
            throw new IllegalArgumentException(var
                    + " environment variable not defined.");
        }

        return value;
    }


}
