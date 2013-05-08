package com.oracle.qa.devsvc;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import static org.testng.Assert.*;


/**
 *
 * @author ADF QA
 */
@Test(groups = {DashboardPage.GROUP, "UI"},
singleThreaded = true)
public class DashboardPage extends TestBase {

    public static final String GROUP = "projectDashboard";

    private final By buildStatus =
            By.cssSelector(".project-latest-activity.build-status");

    private final By authorCommits =
            By.cssSelector(".project-latest-activity.author-commits");

    private final By authorActivity =
            By.cssSelector(".project-latest-activity.author-activity");

    private WebDriver se;

    private static final Logger LOG =
            Logger.getLogger(DashboardPage.class.getName());


    @BeforeClass
    public void beforeClass() throws IOException {
        LOG.info("Starting WebDriver");
        se = getDriver();
        login(se);
    }


    @BeforeMethod
    public void beforeMethod() throws InterruptedException {
        se.get(getServiceURL());
        se.findElement(By.linkText(ProjectPage.PNAME)).click();
        se.findElement(By.linkText("Dashboard")).click();
        Thread.sleep(5000);  // Give AJAX components a chance to load..
    }


    @AfterMethod
    public void afterMethod(ITestResult result) {
        afterMethod(se, result);
    }


    @AfterClass(alwaysRun = true)
    public void afterClasss(ITestContext ctxt) {
        LOG.info("Complete");
        se.quit();
    }


    public void buildStatus() {

        WebElement div = se.findElement(buildStatus);
        String contents = div.getText();

        String text = "Build Status";
        if (!contents.contains(text)) {
            LOG.warning(contents);
            fail("'" + text + "' not visible");
        }

        text = "Sample Maven Build";
        if (!contents.contains(text)) {
            LOG.warning(contents);
            fail("'" + text + "' not visible");
        }

        // Should be at least one Build link
        List<WebElement> links =
                div.findElements(By.tagName("a"));
        assertTrue(links.size() > 0, "No Build Links found");
    }


    public void commitsByAuthor() {

        WebElement div = se.findElement(authorCommits);
        String contents = div.getText();

        String text = "Commits By Author (60 Days)";
        if (!contents.contains(text)) {
            LOG.warning(contents);
            fail("'" + text + "' not visible");
        }

    }


    public void activity() {

        WebElement div = se.findElement(authorActivity);
        String contents = div.getText();

        String text = "Activity (60 Days)";
        if (!contents.contains(text)) {
            LOG.warning(contents);
            fail("'" + text + "' not visible");
        }

    }


    public void pieChart() {
        WebElement div = se.findElement(authorCommits);
        WebElement pie = div.findElement(By.tagName("svg"));
        assertNotNull(pie, "Pie Chart not found");
    }


    public void openTasks() {
        WebElement tbody = se.findElement(By.tagName("tbody"));
        String contents = tbody.getText();

        String text = "Open Tasks";
        if (!contents.contains(text)) {
            LOG.warning(contents);
            fail("'" + text + "' not visible");
        }
    }


    public void closedTasks() {
        WebElement tbody = se.findElement(By.tagName("tbody"));
        String contents = tbody.getText();

        String text = "Closed Tasks";
        if (!contents.contains(text)) {
            LOG.warning(contents);
            fail("'" + text + "' not visible");
        }
    }


    public void commits() {
        WebElement tbody = se.findElement(By.tagName("tbody"));
        String contents = tbody.getText();

        String text = "Commits";
        if (!contents.contains(text)) {
            LOG.warning(contents);
            fail("'" + text + "' not visible");
        }
    }


    public void buildCheckBox() {

        boolean found = false;

        List<WebElement> boxes = se.findElements(By.className("gwt-CheckBox"));

        String text = "Build";

        for (WebElement element : boxes) {
            if (element.getText().contains(text)) {
                found = true;
            }
        }

        if (!found) {
            fail("'" + text + "'  CheckBox not present");
        }

    }


    public void wikiCheckBox() {

        boolean found = false;

        List<WebElement> boxes = se.findElements(By.className("gwt-CheckBox"));

        String text = "Wiki";

        for (WebElement element : boxes) {
            if (element.getText().contains(text)) {
                found = true;
            }
        }

        if (!found) {
            fail("'" + text + "'  CheckBox not present");
        }

    }


    public void tasksCheckBox() {

        boolean found = false;

        List<WebElement> boxes = se.findElements(By.className("gwt-CheckBox"));

        String text = "Tasks";

        for (WebElement element : boxes) {
            if (element.getText().contains(text)) {
                found = true;
            }
        }

        if (!found) {
            fail("'" + text + "'  CheckBox not present");
        }

    }


    public void sourceCheckBox() {

        boolean found = false;

        List<WebElement> boxes = se.findElements(By.className("gwt-CheckBox"));

        String text = "Source";

        for (WebElement element : boxes) {
            if (element.getText().contains(text)) {
                found = true;
            }
        }

        if (!found) {
            fail("'" + text + "'  CheckBox not present");
        }

    }


}
