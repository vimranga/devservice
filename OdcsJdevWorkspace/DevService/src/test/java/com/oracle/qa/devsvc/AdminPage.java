package com.oracle.qa.devsvc;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;


/**
 *
 * @author ADF QA
 */
@Test(groups = {AdminPage.GROUP, "UI"},
singleThreaded = true)
public class AdminPage extends TestBase {

    public static final String ADMINMENU = "admin-menu";

    public static final String CUSTOM_FIELDS = "Custom Fields";

    public static final String EDIT = "Edit";

    public static final String GROUP = "projectAdmin";

    public static final String INLINE_TABS = "inline-tabs";

    public static final String ITERATIONS = "Iterations";

    public static final String MAIN_DIV = "main-content-container-div";

    public static final String PRODUCTS = "Products";

    public static final String SETTINGS = "Settings";

    public static final String SOURCE_CODE = "Source Code";

    public static final String TAGS = "Tags";

    public static final String TASKS = "Tasks";

    public static final String TEAM = "Team";

    private WebDriver se;

    private static final Logger LOG =
            Logger.getLogger(AdminPage.class.getName());


    @BeforeClass
    public void beforeClass() throws IOException {
        LOG.info("Starting WebDriver");
        se = getDriver();
        login(se);
    }


    @BeforeMethod
    public void beforeMethod(Method method) throws InterruptedException {
        LOG.info("Running: " + method.getName());
        se.get(getServiceURL());
        se.findElement(By.linkText(ProjectPage.PNAME)).click();
        se.findElement(By.linkText("Admin")).click();
        Thread.sleep(5000);  // Give AJAX components a chance to load..
    }


    @AfterMethod
    public void afterMethod(ITestResult result) {
        afterMethod(se, result);
    }


    @AfterClass(alwaysRun = true)
    public void afterClasss(ITestContext ctxt) throws Exception {
        LOG.info("Complete");
        se.quit();
    }


    public void settingsTab() {
        WebElement adminMenu = se.findElement(By.className(ADMINMENU));
        adminMenu.findElement(By.linkText(SETTINGS)).click();
        se.findElement(By.id(DCS)).click();
    }


    public void tasksTab() {
        WebElement adminMenu = se.findElement(By.className(ADMINMENU));
        adminMenu.findElement(By.linkText(TASKS)).click();
        se.findElement(By.id(DCS)).click();
    }


    public void sourceCodeTab() {
        WebElement adminMenu = se.findElement(By.className(ADMINMENU));
        adminMenu.findElement(By.linkText(SOURCE_CODE)).click();
        se.findElement(By.id(DCS)).click();
    }


    public void teamTab() {
        WebElement adminMenu = se.findElement(By.className(ADMINMENU));
        adminMenu.findElement(By.linkText(TEAM)).click();
        se.findElement(By.id(DCS)).click();
    }


    public void tasksProductTab() {
        WebElement adminMenu = se.findElement(By.className(ADMINMENU));
        adminMenu.findElement(By.linkText(TASKS)).click();
        WebElement mainDiv = se.findElement(By.id(MAIN_DIV));
        mainDiv.findElement(By.linkText(PRODUCTS)).click();
        se.findElement(By.id(DCS)).click();
    }


    public void tasksIterationsTab() {
        WebElement adminMenu = se.findElement(By.className(ADMINMENU));
        adminMenu.findElement(By.linkText(TASKS)).click();
        WebElement mainDiv = se.findElement(By.id(MAIN_DIV));
        mainDiv.findElement(By.linkText(ITERATIONS)).click();
        se.findElement(By.id(DCS)).click();
    }


    void tasksTagsTab() {
        WebElement adminMenu = se.findElement(By.className(ADMINMENU));
        adminMenu.findElement(By.linkText(TASKS)).click();
        WebElement mainDiv = se.findElement(By.id(MAIN_DIV));
        mainDiv.findElement(By.linkText(TAGS)).click();
        se.findElement(By.id(DCS)).click();
    }


    public void tasksCustomFieldsTab() {
        WebElement adminMenu = se.findElement(By.className(ADMINMENU));
        adminMenu.findElement(By.linkText(TASKS)).click();
        WebElement mainDiv = se.findElement(By.id(MAIN_DIV));
        mainDiv.findElement(By.linkText(CUSTOM_FIELDS)).click();
        se.findElement(By.id(DCS)).click();
    }


    /**
     * Test that Editing and Saving Settings works
     */
    public void editAndSave() {
        se.findElement(By.linkText(EDIT)).click();
        se.findElement(By.xpath(SAVE)).click();
        se.findElement(By.id(DCS)).click();
    }


    /**
     * Test that Editing and then Canceling Settings works
     */
    public void editAndCancel() {
        se.findElement(By.linkText(EDIT)).click();
        se.findElement(By.xpath(CANCEL)).click();
        se.findElement(By.id(DCS)).click();
    }


    /**
     * Click on link 'Add Hosted Repository' should 
     * bring a popup to add repository
     */
    public void addHostedRepostoryPopup() throws InterruptedException {
        WebElement adminMenu = se.findElement(By.className(ADMINMENU));
        adminMenu.findElement(By.linkText(SOURCE_CODE)).click();
        WebElement repos = se.findElement(By.className("hosted-repos"));
        repos.findElement(By.linkText("Add Hosted Repository")).click();

        WebElement popup = se.findElement(By.className("dialogMiddleCenter"));
        popup.findElement(By.className("gwt-TextBox")).sendKeys("Foo");
        popup.findElement(By.xpath(CANCEL)).click();
    }


    /**
     * Click on link 'Add Hosted Repository' should 
     * bring a popup to add repository
     */
    public void addExternalRepostoryPopup() throws InterruptedException {
        WebElement adminMenu = se.findElement(By.className(ADMINMENU));
        adminMenu.findElement(By.linkText(SOURCE_CODE)).click();
        WebElement repos = se.findElement(By.className("external-repos"));
        repos.findElement(By.linkText("Add External Repository")).click();

        WebElement popup = se.findElement(By.className("dialogMiddleCenter"));
        popup.findElement(By.className("gwt-TextBox")).sendKeys("Foo");
        popup.findElement(By.xpath(CANCEL)).click();
    }


}
