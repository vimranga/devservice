package com.oracle.qa.devsvc;

import java.io.IOException;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 * Tests that the basic elements are present on the Project Page.
 *
 * @author ADF QA
 */
@Test(groups = {ProjectPage.GROUP, "UI"},
timeOut = 300000,
singleThreaded = true)
public class ProjectPage extends TestBase {

    public static final String GROUP = "projectPage";

    private WebDriver se;

    public static final String PNAME = "AutomationProject2";

    private static final Logger LOG =
            Logger.getLogger(ProjectPage.class.getName());


    @BeforeClass
    public void beforeClass() throws IOException {
        LOG.info("Starting WebDriver");
        se = getDriver();
        login(se);
    }


    @BeforeMethod
    public void beforeMethod() {
        se.get(getServiceURL());
        se.findElement(By.linkText(PNAME)).click();
    }


    @AfterMethod
    public void afterMethod(ITestResult result) {
        afterMethod(se, result);
    }


    @AfterClass(alwaysRun = true)
    public void afterClass() {
        LOG.info("Complete");
        se.quit();
    }


    public void dashboardTab() {
        se.findElement(By.linkText("Dashboard")).click();
        se.findElement(By.id(DCS)).click();
    }


    public void tasksTab() {
        se.findElement(By.linkText("Tasks")).click();
        se.findElement(By.id(DCS)).click();
    }


    public void browseTab() {
        se.findElement(By.linkText("Browse")).click();
        se.findElement(By.id(DCS)).click();
    }


    public void buildTab() {
        se.findElement(By.linkText("Build")).click();
        se.findElement(By.id(DCS)).click();
    }


    public void deployTab() {
        se.findElement(By.linkText("Deploy")).click();
        se.findElement(By.id(DCS)).click();
    }


    public void wikiTab() {
        se.findElement(By.linkText("Wiki")).click();
        se.findElement(By.id(DCS)).click();
    }


    public void teamTab() {
        se.findElement(By.linkText("Team")).click();
        se.findElement(By.id(DCS)).click();
    }


    public void adminTab() {
        se.findElement(By.linkText("Admin")).click();
        se.findElement(By.id(DCS)).click();
    }


    public void homeTab() {
        se.findElement(By.linkText("Home")).click();
        se.findElement(By.id(DCS)).click();
    }


}
