package com.oracle.qa.devsvc;


import java.io.IOException;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.testng.Assert.*;
import org.testng.ITestResult;
import org.testng.annotations.*;

import com.oracle.qa.devsvc.bean.ProjectBean;

import com.oracle.qa.devsvc.utils.OdcsUtils;

import org.openqa.selenium.support.ui.Select;

import org.testng.Assert;


/**
 *
 * @author ADF QA
 */
@Test(groups = { LandingPage.GROUP, "UI" }, singleThreaded = true)
public class LandingPage extends TestBase {

    public static final String GROUP = "landingPage";

    private WebDriver se;

    final String pNameToDelete = "AutomationProject3ToDelete";

    private static final Logger LOG =
        Logger.getLogger(LandingPage.class.getName());


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
    public void afterClass() {
        se.quit();
    }


    public void signIn() {
        login(se);
    }

    /*public void signInAsNonAdmin() {
        loginAsNonAdmin(se);
    }*/


     @Test(dependsOnMethods = { "signIn" }, dataProvider = "AutomationProject1",
          dataProviderClass = OdcsDataProvider.class)
    public void createProject1(ProjectBean prjBean) throws Exception {
        createProjectInternal(prjBean);

    }

    @Test(dependsOnMethods = { "createProject1" })
    public void setUseProjectAsTemplate() throws Exception {
        final String saveButton =
            "(//button[contains(text(),'Save Changes')])";

        se.findElement(By.id(DCS)).click();
        se.navigate().refresh();
        se.findElement(By.linkText("AutomationProject1")).click();

        se.findElement(By.id("admin")).click();
        se.findElement(By.linkText("Edit")).click();
        WebElement useAsTemplate =
            se.findElement(By.xpath("//span[@class='gwt-CheckBox']/input"));
        LOG.info("isTemplate" + useAsTemplate);
        useAsTemplate.click();
        System.out.println("useAsTemplate:" + useAsTemplate.isSelected());
        se.findElement(By.xpath(saveButton)).click();

    }


    @Test(dependsOnMethods = { "setUseProjectAsTemplate" },
          dataProvider = "AutomationProject2",
          dataProviderClass = OdcsDataProvider.class)
    public void createProject2(ProjectBean prjBean) throws Exception {
        createProjectInternal(prjBean);

    }

    @Test(dependsOnMethods = { "signIn" }, dataProvider = "AutomationProject3",
          dataProviderClass = OdcsDataProvider.class)
    public void createProject3(ProjectBean prjBean) throws Exception {
        createProjectInternal(prjBean);

    }

    @Test(dependsOnMethods = { "signIn" },
          dataProvider = "AutomationProjectNonAdmin",
          dataProviderClass = OdcsDataProvider.class)
    public void createProjectNonAdmin(ProjectBean prjBean) throws Exception {
        createProjectInternal(prjBean);

    }


    private void createProjectInternal(ProjectBean prjBean) throws Exception {

        se.findElement(By.id(DCS)).click();
        se.navigate().refresh();

        String saveProjectButton =
            "(//button[contains(text(),'Create Project')])";

        se.findElement(By.linkText("Create Project")).click();
        se.findElement(By.name("project-name")).clear();
        se.findElement(By.name("project-name")).sendKeys(prjBean.getProjectName());
        se.findElement(By.cssSelector("textarea.text")).clear();
        se.findElement(By.cssSelector("textarea.text")).sendKeys(prjBean.getProjectDesc());

        LOG.info("Projectname:" + prjBean.getProjectName());

        String prjPrivacy = prjBean.getProjectPrivacy();
        WebElement privacy = null;

        LOG.info("prjPrivacy:" + prjPrivacy);

        if (prjPrivacy.equals("Organization Private"))
            privacy =
                    se.findElement(By.xpath("//p[text()=' Organization Private ']//input[@name='privacy']"));
        else if (prjPrivacy.equals("Private"))
            privacy =
                    se.findElement(By.xpath("//p[text()=' Private ']//input[@name='privacy']"));
        privacy.click();


        LOG.info("projectprivacy done");

        String wikiMarkup = prjBean.getWikiMarkup();

        LOG.info("wikiMarkup" + wikiMarkup);

        WebElement dropDownListBox =
            se.findElement(By.className("gwt-ListBox"));
        Select clickThis = new Select(dropDownListBox);
        clickThis.selectByVisibleText(wikiMarkup);

        if (prjBean.isCreateFromTemplate()) {

            WebElement isTemplate =
                se.findElement(By.xpath("//span[@class='gwt-CheckBox']/input"));
            LOG.info("isTemplate" + isTemplate);

            System.out.println(isTemplate.isSelected());
            isTemplate.click();
            System.out.println(isTemplate.isSelected());
            LOG.info("isTemplate done");

            WebElement option =
                se.findElement(By.xpath("//select/option[text()='" +
                                        prjBean.getTemplatePrjName() + "']"));
            option.click();

        }


        se.findElement(By.xpath(saveProjectButton)).click();
        LOG.info("Clicked Save");
        Thread.sleep(5000);
        capturePage(se, "createProject.png");
        se.findElement(By.id(DCS)).click();
        se.navigate().refresh();

        se.findElement(By.linkText(prjBean.getProjectName())).click();
        se.findElement(By.className("about-project"));
        se.findElement(By.className("project-latest-activity"));
        se.findElement(By.className("project-mavan-repo"));


    }


    @Test(dependsOnMethods = { "signIn" })
    public void cancelCreateProject() throws Exception {

        se.findElement(By.id(DCS)).click();
        se.navigate().refresh();

        String cancelButton = "(//button[contains(text(),'Cancel')])";

        se.findElement(By.linkText("Create Project")).click();
        se.findElement(By.name("project-name")).clear();
        se.findElement(By.name("project-name")).sendKeys(pNameToDelete);
        se.findElement(By.cssSelector("textarea.text")).clear();
        se.findElement(By.cssSelector("textarea.text")).sendKeys("Test Project");
        se.findElement(By.xpath(cancelButton)).click();

    }


    @Test(dependsOnMethods = { "signIn" })
    public void projectDashboards() throws IOException {

        se.findElement(By.id(DCS)).click();
        se.navigate().refresh();

        List<WebElement> elements =
            se.findElements(By.linkText("View Project Dashboard"));

        LOG.info("Dashboards Found: " + elements.size());
        assertTrue(elements.size() > 0);


         // Need to gather links from elements since once the page is
         // changed the elements will become stale

        List<String> links = new LinkedList();

        for (WebElement element : elements) {
            links.add(element.getAttribute("href").toString());
        }

        for (String link : links) {
            LOG.info("Validating: " + link);
            se.get(link);
            se.findElement(By.className("build-status"));
            se.findElement(By.className("author-commits"));
            se.findElement(By.className("author-activity"));
            se.findElement(By.className("project-latest-activity"));
            se.findElement(By.className("dashboardActivityPanel"));
        }

    }


    @Test(dependsOnMethods = { "signIn" })
    public void verifyAllProjects() throws IOException {

        se.findElement(By.id(DCS)).click();
        se.navigate().refresh();

        WebElement projectList =
            se.findElement(By.className("discover-project-list"));

        List<WebElement> elements =
            projectList.findElements(By.className("misc-icon"));


         // Need to gather links from elements since once the page is
         // changed the elements will become stale

        List<String> links = new LinkedList();

        for (WebElement element : elements) {
            if (element.getTagName().equals("a")) {
                links.add(element.getAttribute("href").toString());
            }
        }

        for (String link : links) {
            LOG.info("Validating: " + link);
            se.get(link);
            se.findElement(By.className("about-project"));
            se.findElement(By.className("project-latest-activity"));
            se.findElement(By.className("project-mavan-repo"));
        }


    }

    @Test(dependsOnMethods = { "createProject1" })
    public void verifyHeader() {
        se.findElement(By.id(DCS)).click();
        se.navigate().refresh();
        se.findElement(By.linkText("AutomationProject1")).click();
        se.findElement(By.linkText("Developer Cloud Service")).click();
        se.findElement(By.linkText("AutomationProject1")).click();
    }

    @Test(dependsOnMethods = { "signIn" })
    public void verifyFooter() {
        se.findElement(By.id(DCS)).click();
        se.navigate().refresh();
        se.findElement(By.linkText("About Oracle"));
        se.findElement(By.linkText("Contact Us"));
        se.findElement(By.linkText("Legal Notices"));
        se.findElement(By.linkText("Terms of Use"));
        se.findElement(By.linkText("Your Privacy Rights"));
    }


    @Test(dependsOnMethods = { "createProject1" })
    public void verifyLogoLink() {
        se.findElement(By.id(DCS)).click();
        se.navigate().refresh();
        se.findElement(By.linkText("AutomationProject1")).click();
        se.findElement(By.linkText("Developer Cloud Service")).click();
        se.findElement(By.linkText("AutomationProject1")).click();


    }


    @Test(dependsOnMethods = { "createProject3" })
    public void deleteProject() throws IOException {

        se.findElement(By.id(DCS)).click();
        se.navigate().refresh();
        se.findElement(By.linkText(pNameToDelete)).click();

        se.findElement(By.id("admin")).click();
        se.findElement(By.linkText("Delete Project")).click();
        WebElement delete =
            se.findElement(By.xpath("//span[@class='gwt-CheckBox']/input"));
        LOG.info("delete" + delete);

        System.out.println(delete.isSelected());
        delete.click();
        System.out.println(delete.isSelected());
        LOG.info("delete done");
        se.findElement(By.cssSelector("button.button.special")).click();

        se.findElement(By.id(DCS)).click();
        se.navigate().refresh();

    }



    @Test(dependsOnMethods = { "signIn" })
    public void createProject2() throws IOException {
    }


    @Test(dependsOnMethods = { "createProject2" })
    public void orgSharedTab() throws Exception {
        se.findElement(By.id(DCS)).click();
        se.navigate().refresh();
        se.findElement(By.linkText("Organization Shared")).click();

        Thread.sleep(500);
        //Add a assert that element is not present
        String locator = "//a[text()='AutomationProject1']";
        boolean result = OdcsUtils.verifyElementPresent(locator, se);
        Assert.assertEquals(result, false);
        String locator1 = "//a[text()='AutomationProject2']";
        result = OdcsUtils.verifyElementPresent(locator1, se);
        Assert.assertEquals(result, true);
    }


    @Test(dependsOnMethods = { "createProject2" })
    public void memberTab() throws Exception {
        se.findElement(By.id(DCS)).click();
        se.navigate().refresh();
        se.findElement(By.linkText("Member")).click();
        Thread.sleep(500);
        verifyProjectList();
    }


    @Test(dependsOnMethods = { "createProject2" })
    public void ownerTab() throws Exception {
        se.findElement(By.id(DCS)).click();
        se.navigate().refresh();
        se.findElement(By.linkText("Owner")).click();
        Thread.sleep(500);
        verifyProjectList();
    }


    @Test(dependsOnMethods = { "createProject2" })
    public void allTab() throws Exception {
        se.findElement(By.id(DCS)).click();
        se.navigate().refresh();
        se.findElement(By.linkText("All")).click();
        Thread.sleep(500);
        verifyProjectList();
    }


       @Test(dependsOnMethods = { "signIn" })
    public void editOrganization() throws Exception {

        se.findElement(By.id(DCS)).click();
        se.navigate().refresh();

        final String textArea = "textarea.gwt-TextArea.text";
        final String saveButton =
            "(//button[contains(text(),'Save and Close')])";
        String text =
            "This is an organization to test OPC Developer Service." + "\n" +
            new Date();
        final String nameArea = "(//input[@type='text'])";
        String nameText = "AfterEditService";
        final String wikiSelect =
            "(//div[@id='project-admin']/fieldset[3]/div/select)";
        String wikiText = "Confluence";

        se.findElement(By.linkText("Edit Organization")).click();

        se.findElement(By.xpath(nameArea)).clear();
        se.findElement(By.xpath(nameArea)).sendKeys(nameText);

        se.findElement(By.cssSelector(textArea)).clear();
        se.findElement(By.cssSelector(textArea)).sendKeys(text);

        se.findElement(By.xpath(wikiSelect)).sendKeys(wikiText);
        se.findElement(By.xpath(saveButton)).click();

        Thread.sleep(2500);

        WebElement bodyTag = se.findElement(By.tagName("body"));

        if (!bodyTag.getText().contains("Organization Updated")) {
            capturePage(se, "editOrganization-fail.png");
            fail("Failed to Update Organization");
        }
    }


    @Test(dependsOnMethods = { "signIn" })
    public void cancelEditOrganization() throws Exception {

        se.findElement(By.id(DCS)).click();
        se.navigate().refresh();

        final String textArea = "textarea.gwt-TextArea.text";
        final String cancelButton = "(//button[contains(text(),'Cancel')])";
        String text =
            "This is an organization to test OPC Developer Service." + "\n" +
            new Date();

        se.findElement(By.linkText("Edit Organization")).click();
        se.findElement(By.cssSelector(textArea)).clear();
        se.findElement(By.cssSelector(textArea)).sendKeys(text);
        se.findElement(By.xpath(cancelButton)).click();

    } 


    // All private methods

    private void verifyProjectList() throws IOException {

        LOG.info("Inside projectList method");
        se.findElement(By.id(DCS)).click();
        se.navigate().refresh();

        WebElement projectList =
            se.findElement(By.className("discover-project-list"));
        LOG.info("projectList:" + projectList);

        List<WebElement> elements =
            projectList.findElements(By.className("misc-icon"));

        /*
         * Need to gather links from elements since once the page is
         * changed the elements will become stale
         */
        List<String> links = new LinkedList();
        int validProject = 0;
        boolean listVerified = false;

        for (WebElement element : elements) {
            LOG.info("element.getText()" + element.getText());
            if (element.getText().equals("AutomationProject1") ||
                element.getText().equals("AutomationProject2")) {
                validProject++;
                LOG.info("validProject" + validProject);
                if (validProject == 2)
                    break;
            }
        }
        Assert.assertEquals(validProject, 2);


    }


}
