package com.oracle.qa.devsvc;

import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.Date;
import org.testng.annotations.BeforeMethod;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.ITestContext;
import java.io.IOException;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.testng.Assert.*;


/**
 *
 * @author ADF QA
 */
@Test(groups = {TasksPage.GROUP, "UI"},
singleThreaded = true)
public class TasksPage extends TestBase {

    public static final String ADVANCED_SEARCH = "Advanced Search";

    public static final String ALL_TASKS = "All tasks";

    public static final String ASSIGNED_TO_ME = "Assigned to me";

    public static final String GROUP = "projectTasks";

    public static final String HOME_TAB = "Home";

    public static final String OPEN_TASKS = "Open tasks";

    public static final String RECENTLY_CHANGED = "Recently changed";

    public static final String RELATED_TO_ME = "Related to me";

    public static final String TASKS_TAB = "Tasks";

    public static final String NEW_TASK =
            "(//button[contains(text(),'New Task')])";

    public static final String CREATE_TASK =
            "(//button[contains(text(),'Create Task')])";

    public static final String CANCEL =
            "(//button[contains(text(),'Cancel')])";

    private WebDriver se;

    private static final Logger LOG =
            Logger.getLogger(TasksPage.class.getName());


    @BeforeClass
    public void beforeClass() throws IOException {
        LOG.info("Starting WebDriver");
        se = getDriver();
        login(se);
    }


    @BeforeMethod
    public void beforeMethod() {
        se.get(getServiceURL());
        se.findElement(By.linkText(ProjectPage.PNAME)).click();
        se.findElement(By.linkText(TASKS_TAB)).click();
    }


    @AfterMethod
    public void afterMethod(ITestResult result) {
        afterMethod(se, result);
    }


    @AfterClass()
    public void afterClasss(ITestContext ctxt) {
        se.quit();
    }


    public void tasksTab() {
        se.findElement(By.linkText(TASKS_TAB)).click();
        se.findElement(By.id(DCS)).click();
    }


    public void missingTaskSummary() throws Exception {

        se.findElement(By.xpath(NEW_TASK)).click();
        se.findElement(By.xpath(CREATE_TASK)).click();

        Thread.sleep(2500);

        WebElement popup = se.findElement(By.className("mole-text"));

        String text = "A summary is required.";

        if (!popup.getText().contains(text)) {
            fail("'" + text + "' not found");
        }

        popup.findElement(By.linkText("Close")).click();
        se.findElement(By.xpath(CANCEL)).click();
        se.findElement(By.id(DCS)).click();
    }


    public void cancelTaskCreation() throws Exception {
        se.findElement(By.xpath(NEW_TASK)).click();
        se.findElement(By.xpath(CREATE_TASK)).click();
        se.findElement(By.xpath(CANCEL)).click();
        se.findElement(By.id(DCS)).click();
    }


    public void createTask() throws Exception {

        String summary = "QA Automation Test Task (" + new Date() + ")";
        String description = "QA Automation Test Task Description ("
                + new Date() + ")";

        se.findElement(By.xpath(NEW_TASK)).click();

        se.findElement(By.cssSelector("input.summary.left")).clear();
        se.findElement(By.cssSelector("input.summary.left")).sendKeys(summary);
        se.findElement(By.cssSelector("input.gwt-SuggestBox")).clear();
        se.findElement(By.cssSelector("input.gwt-SuggestBox")).sendKeys("QA-Test");
        se.findElement(By.cssSelector("textarea.textarea")).clear();
        se.findElement(By.cssSelector("textarea.textarea")).sendKeys(description);
        se.findElement(By.xpath(CREATE_TASK)).click();

        Thread.sleep(5000);

        WebElement popup = se.findElement(By.className("mole-text"));

        String text = "Task Created";

        if (!popup.getText().contains(text)) {
            fail("'" + text + "' not found");
        }

        popup.findElement(By.linkText("Close")).click();

        // Verify Description 
        WebElement div = se.findElement(By.className("credentials"));
        if (!div.getText().contains(description)) {
            fail("Task Description not visible");
        }

        // Verify Summary 
        div = se.findElement(By.cssSelector(".left.GM1YMQECKM"));

        if (!div.getText().contains(summary)) {
            fail("Task Summary not visible");
        }

        // Verify Summary is visible under Tasks Tab
        se.findElement(By.linkText(TASKS_TAB)).click();

        WebDriverWait wait = new WebDriverWait(se, 30);
        By summaryDiv = By.cssSelector(".content.right");

        ExpectedCondition<Boolean> condition =
                ExpectedConditions.textToBePresentInElement(summaryDiv,
                summary);
        wait.until(condition);

        div = se.findElement(summaryDiv);
        if (!div.getText().contains(summary)) {
            fail("Task Summary not visible under Tasks Tab");
        }

        // Verify Summary is visible in Home Tab under latest activity
        se.findElement(By.linkText(HOME_TAB)).click();

        By latestActivity = By.className("project-latest-activity");
        condition = ExpectedConditions.textToBePresentInElement(latestActivity,
                summary);
        wait.until(condition);

        div = se.findElement(latestActivity);
        if (!div.getText().contains(summary)) {
            fail("Task Summary not visible under Latest Activities");
        }

        se.findElement(By.id(DCS)).click();
    }


    public void searchTasks() throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(se, 30);

        By table = By.xpath("//table[@class='tasks']");
        By rows = By.xpath("//table[@class='tasks']/tbody/tr");
        By searchButton = By.className("search-button");

        ExpectedCondition<WebElement> condition =
                ExpectedConditions.visibilityOfElementLocated(rows);
        wait.until(condition);

        int initialCount = se.findElements(rows).size();

        for (int i = 0; i < 10; i++) {
            initialCount = se.findElements(rows).size();
            if (initialCount > 1) {
                break;
            }
            Thread.sleep(2500);
        }

        LOG.info("Rows: " + initialCount);
        WebElement search = se.findElement(By.className("search"));
        search.sendKeys("Bogus Search Entry");
        se.findElement(searchButton).click();

        Thread.sleep(5000);
        int count = se.findElements(rows).size();
        LOG.info("Rows: " + count);
        assertEquals(count, 1);

        search.clear();
        se.findElement(searchButton).click();
        Thread.sleep(5000);

        count = se.findElements(rows).size();
        LOG.info("Rows: " + count);
        assertEquals(count, initialCount);
    }


    public void allTasksStandardSearch() {
        se.findElement(By.linkText(ALL_TASKS)).click();
        se.findElement(By.linkText("JSON"));
        se.findElement(By.linkText("CSV"));
    }


    public void assignedToMeStandardSearch() {
        se.findElement(By.linkText(ASSIGNED_TO_ME)).click();
        se.findElement(By.linkText("JSON"));
        se.findElement(By.linkText("CSV"));
    }


    public void openTasksStandardSearch() {
        se.findElement(By.linkText(OPEN_TASKS)).click();
        se.findElement(By.linkText("JSON"));
        se.findElement(By.linkText("CSV"));
    }


    public void recentlyChangedStandardSearch() {
        se.findElement(By.linkText(RECENTLY_CHANGED)).click();
        se.findElement(By.linkText("JSON"));
        se.findElement(By.linkText("CSV"));
    }


    public void releatedToMeStandardSearch() {
        se.findElement(By.linkText(RELATED_TO_ME)).click();
        se.findElement(By.linkText("JSON"));
        se.findElement(By.linkText("CSV"));
    }


    public void advancedSearch() {
        se.findElement(By.linkText(ADVANCED_SEARCH)).click();
    }


}