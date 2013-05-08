package com.oracle.qa.devsvc;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.ITestContext;
import java.lang.reflect.Method;
import java.io.IOException;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.testng.Assert.*;


/**
 *
 * @author ADF QA
 */
@Test(groups = {ProjectHomePage.GROUP, "UI"},
singleThreaded = true)
public class ProjectHomePage extends TestBase {

    public static final String GROUP = "projectHome";

    private WebDriver se;

    private static final Logger LOG =
            Logger.getLogger(ProjectHomePage.class.getName());


    @BeforeClass
    public void beforeClass() throws IOException {
        LOG.info("Starting WebDriver");
        se = getDriver();
    }


    @AfterMethod
    public void afterMethod(ITestResult result) {
        afterMethod(se, result);
    }


    @AfterClass()
    public void afterClasss(ITestContext ctxt) {
        se.quit();
    }


    public void signIn() {
        login(se);
    }


    @Test(dependsOnMethods = {"signIn"})
    public void selectProject() {
        se.findElement(By.id(DCS)).click();
        se.navigate().refresh();
        se.findElement(By.linkText(ProjectPage.PNAME)).click();
    }


    @Test(dependsOnMethods = {"selectProject"})
    public void homeTab() {
        se.findElement(By.linkText("Home")).click();
    }


    @Test(dependsOnMethods = {"homeTab"})
    public void readMoreLink() {
        se.findElement(By.linkText("Read More")).click();
    }


    @Test(dependsOnMethods = {"homeTab"})
    public void aboutProjectSection() {
        final String text = "About Project";
        WebElement elem = se.findElement(By.className("about-project"));
        if (!elem.getText().contains(text)) {
            fail("'" + text + "' not found");
        }
    }


    @Test(dependsOnMethods = {"homeTab"})
    public void sourcesSection() {

        WebElement elem =
                se.findElement(By.className("project-sources"));
        String text = "Git source code repositories are located at";

        if (!elem.getText().contains(text)) {
            fail("'" + text + "' not found");
        }
    }


    @Test(dependsOnMethods = {"homeTab"})
    public void mavenSection() {

        WebElement elem =
                se.findElement(By.className("project-mavan-repo"));

        String text = "The Maven repository for this project is located at:";

        if (!elem.getText().contains(text)) {
            fail("'" + text + "' not found");
        }
    }


    @Test(dependsOnMethods = {"homeTab"})
    public void latestActivitySection() {

        WebElement elem =
                se.findElement(By.className("project-latest-activity"));

        String text = "Latest Activity";

        if (!elem.getText().contains(text)) {
            fail("'" + text + "' not found");
        }
    }


}
