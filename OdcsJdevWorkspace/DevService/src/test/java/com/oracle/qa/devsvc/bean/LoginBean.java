package com.oracle.qa.devsvc.bean;

public class LoginBean{

   public LoginBean()
   {
      super();
   }

private String projectURL;

    private String serviceURL;

    private String userName;

    private String password;

  public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public String getProjectURL() {
        return projectURL;
    }


    public String getServiceURL() {
        return serviceURL;
    }


    public void setServiceURL(String serviceURL) {
        this.serviceURL = serviceURL;
    }


    public void setProjectURL(String projectURL) {
        this.projectURL = projectURL;
    }


    public String getUserName() {
        return userName;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }

}
