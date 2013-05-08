package com.oracle.qa.devsvc.bean;

public class ProjectBean{

   public ProjectBean()
   {
      super();
   }

   private String projectName;
   private String projectDesc;
   private String projectPrivacy;
   private String wikiMarkup;
   private boolean createFromTemplate;
   private String templatePrjName;

   public void setProjectName(String projectName)
   {
      this.projectName = projectName;
   }

   public String getProjectName()
   {
      return projectName;
   }

   public void setProjectDesc(String projectDesc)
   {
      this.projectDesc = projectDesc;
   }

   public String getProjectDesc()
   {
      return projectDesc;
   }

   public void setProjectPrivacy(String projectPrivacy)
   {
      this.projectPrivacy = projectPrivacy;
   }

   public String getProjectPrivacy()
   {
      return projectPrivacy;
   }

   public void setWikiMarkup(String wikiMarkup)
   {
      this.wikiMarkup = wikiMarkup;
   }

   public String getWikiMarkup()
   {
      return wikiMarkup;
   }

   public void setCreateFromTemplate(boolean createFromTemplate)
   {
      this.createFromTemplate = createFromTemplate;
   }

   public boolean isCreateFromTemplate()
   {
      return createFromTemplate;
   }
   public void setTemplatePrjName(String templatePrjName)
   {
      this.templatePrjName = templatePrjName;
   }

   public String getTemplatePrjName()
   {
      return templatePrjName;
   }

}

