package com.oracle.qa.devsvc;

import java.util.Random;
import org.eclipse.jgit.api.AddCommand;
import java.util.Date;
import java.io.FileWriter;
import org.eclipse.jgit.api.CommitCommand;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
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
@Test(groups = {TeamPage.GROUP, "UI"},
singleThreaded = true)
public class TeamPage extends TestBase {

    public static final String GROUP = "projectTeam";

    private WebDriver se;

    private static final Logger LOG =
            Logger.getLogger(TeamPage.class.getName());


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
        se.findElement(By.linkText("Team")).click();
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


    /**
     * Verify that the user we're running as is listed
     */
    public void userIsListed() {
        WebElement e = se.findElement(By.className("project-detail-container"));
        assertTrue(e.getText().contains(getUserName()));
    }


    /**
     * Verify that 'Manage Members' button takes user to Admin page
     */
    public void manageMembers() {
        String addUser = "(//button[contains(text(),'Add to project')])";
        se.findElement(By.linkText("Manage Members")).click();
        WebElement e = se.findElement(By.xpath(addUser));
        assertNotNull(e);
    }


}
