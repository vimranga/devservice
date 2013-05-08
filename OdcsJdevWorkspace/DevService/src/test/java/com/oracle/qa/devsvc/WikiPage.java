package com.oracle.qa.devsvc;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Random;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.annotations.*;
import static org.testng.Assert.*;


/**
 *
 * @author ADF QA
 */
@Test(groups = {WikiPage.GROUP, "UI"},
timeOut = 300000,
singleThreaded = true)
public class WikiPage extends TestBase {

    public static final String GROUP = "projectWiki";

    public static final String TEXT = "textarea";

    public static final String TITLE = "input.input";

    private WebDriver se;

    public static final String PNAME = "Main Testplan";

    public static final String NEW_PAGE =
            "(//button[contains(text(),'New Page')])";

    private static final Logger LOG =
            Logger.getLogger(ProjectPage.class.getName());

    private String title;

    private String editText;


    @BeforeClass
    public void beforeClass() throws IOException {
        LOG.info("Starting WebDriver");
        se = getDriver();
        login(se);
    }


    @BeforeMethod
    public void beforeMethod(Method m) throws InterruptedException {
        LOG.info("Running: " + m.getName());
        se.get(getServiceURL());
        se.findElement(By.linkText(PNAME)).click();
        se.findElement(By.linkText("Wiki")).click();
        Thread.sleep(5000);  // Give AJAX components a chance to load..
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


    void cancelEdits() {
        se.findElement(By.xpath(NEW_PAGE)).click();
        se.findElement(By.cssSelector(TITLE)).sendKeys("Test");
        se.findElement(By.tagName(TEXT)).sendKeys("Sample Text");
        se.findElement(By.xpath(CANCEL)).click();
        se.findElement(By.id(DCS)).click();
    }


    public void newPage() {
        se.findElement(By.xpath(NEW_PAGE)).click();
        title = "QA TestNG Entry " + new Random().nextInt(1000000);
        se.findElement(By.cssSelector(TITLE)).sendKeys(title);
        se.findElement(By.tagName(TEXT)).sendKeys("Sample Text + ("
                + new Date() + ")\n");
        se.findElement(By.xpath(SAVE)).click();
        se.findElement(By.id(DCS)).click();
    }


    @Test(dependsOnMethods = {"newPage"})
    public void editPage() {
        se.findElement(By.linkText(title)).click();
        se.findElement(By.linkText("Edit")).click();
        editText = "Sample Edit Text + (" + new Date() + ")\n";
        se.findElement(By.tagName(TEXT)).sendKeys(editText);
        se.findElement(By.xpath(SAVE)).click();
        se.findElement(By.id(DCS)).click();
    }


    @Test(dependsOnMethods = {"editPage"})
    public void searchPages() throws InterruptedException {

        String contentDiv = "div.content";
        String search = "BogusEntry12411";

        WebElement content = se.findElement(By.cssSelector(contentDiv));
        assertTrue(content.getText().contains(title));

        se.findElement(By.cssSelector("input.gwt-TextBox")).sendKeys(search);
        se.findElement(By.xpath(SEARCH)).click();
        Thread.sleep(5000);
        content = se.findElement(By.cssSelector(contentDiv));
        assertFalse(content.getText().contains(title));

        se.findElement(By.cssSelector("input.gwt-TextBox")).clear();
        se.findElement(By.cssSelector("input.gwt-TextBox")).sendKeys(editText);
        se.findElement(By.xpath(SEARCH)).click();
        Thread.sleep(5000);
        content = se.findElement(By.cssSelector(contentDiv));
        assertTrue(content.getText().contains(title));
    }


    @Test(dependsOnMethods = {"searchPages"})
    public void deletePage() throws InterruptedException {
        se.findElement(By.linkText(title)).click();
        se.findElement(By.linkText("Delete")).click();
        Thread.sleep(5000);
        WebElement mole = se.findElement(By.className("mole-text"));

        String text = "Page Deleted.";

        if (!mole.getText().contains(text)) {
            fail("'" + text + "' not found");
        }

        se.findElement(By.id(DCS)).click();
    }


    @Test(dependsOnMethods = {"deletePage"})
    public void deleteComplete() throws InterruptedException {
        String contentDiv = "div.content";
        WebElement content = se.findElement(By.cssSelector(contentDiv));
        assertFalse(content.getText().contains(title));
    }


}