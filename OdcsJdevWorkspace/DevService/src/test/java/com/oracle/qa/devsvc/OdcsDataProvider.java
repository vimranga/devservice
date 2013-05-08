package com.oracle.qa.devsvc;

import org.testng.annotations.DataProvider;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import com.oracle.qa.devsvc.bean.ProjectBean;

/**
 *
 * @author ADF QA
 */
public class OdcsDataProvider{

   public OdcsDataProvider()
   {
      super();
   }


   //This function will provide the patameter data for creating projects

   @DataProvider(name = "AutomationProject1")
   public static Iterator<Object[]> AutomationProject1()
   {

	System.out.println("DP Called");
      List<Object[]> returnVal = new LinkedList<Object[]>(); 

      List<ProjectBean> args = new LinkedList<ProjectBean>();

      ProjectBean obj = null, obj1 = null, obj2 = null;

      obj = new ProjectBean();
      obj.setProjectName("AutomationProject1");
      obj.setProjectDesc("Project1 for automation tests");
      obj.setProjectPrivacy("Private");
      obj.setWikiMarkup("Confluence");
      obj.setCreateFromTemplate(false);
      args.add(obj);      

      returnVal.add(new Object[]{obj});   

      return returnVal.iterator(); 

	
   }
    @DataProvider(name = "AutomationProject2")
    public static Iterator<Object[]> AutomationProject2()
    {

         System.out.println("DP Called");
       List<Object[]> returnVal = new LinkedList<Object[]>(); 

       List<ProjectBean> args = new LinkedList<ProjectBean>();

       ProjectBean obj1 = null;

       

       obj1 = new ProjectBean();
       obj1.setProjectName("AutomationProject2");
       obj1.setProjectDesc("Project2 for automation tests");
       obj1.setProjectPrivacy("Organization Private");
       obj1.setWikiMarkup("Textile");
       obj1.setCreateFromTemplate(true);
       obj1.setTemplatePrjName("AutomationProject1");
       args.add(obj1);

       

       returnVal.add(new Object[]{obj1});

       return returnVal.iterator(); 

         
    }
    @DataProvider(name = "AutomationProject3")
    public static Iterator<Object[]> AutomationProject3()
    {

         System.out.println("DP Called");
       List<Object[]> returnVal = new LinkedList<Object[]>(); 

       List<ProjectBean> args = new LinkedList<ProjectBean>();

       ProjectBean obj2 = null;       

       obj2 = new ProjectBean();
       obj2.setProjectName("AutomationProject3ToDelete");
       obj2.setProjectDesc("Project3 for automation tests");
       obj2.setProjectPrivacy("Organization Private");
       obj2.setWikiMarkup("Textile");
       obj2.setCreateFromTemplate(false);
       args.add(obj2);

       returnVal.add(new Object[]{obj2});

       return returnVal.iterator(); 

         
    }
    
    @DataProvider(name = "AutomationProjectNonAdmin")
    public static Iterator<Object[]> AutomationProjectNonAdmin()
    {

         System.out.println("DP Called");
       List<Object[]> returnVal = new LinkedList<Object[]>(); 

       List<ProjectBean> args = new LinkedList<ProjectBean>();

       ProjectBean obj2 = null;       

       obj2 = new ProjectBean();
       obj2.setProjectName("AutomationProjectNonAdmin");
       obj2.setProjectDesc("Non Admin project for automation tests");
       obj2.setProjectPrivacy("Organization Private");
       obj2.setWikiMarkup("Textile");
       obj2.setCreateFromTemplate(false);
       args.add(obj2);

       returnVal.add(new Object[]{obj2});

       return returnVal.iterator(); 

         
    }
}

