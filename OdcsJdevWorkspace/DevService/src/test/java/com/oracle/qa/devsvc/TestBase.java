package com.oracle.qa.devsvc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;



/**
 *
 * @author ADF QA
 */
public class TestBase {

    private String projectURL;

    private String serviceURL;

    private String userName;

    private String password;
    
    private String nonAdminUserName;

    private String nonAdminPassword;

 
    final String jquery = "http://code.jquery.com/jquery-1.7.1.js";

    final String DCS = "productName";

    public static final String SAVE = "(//button[contains(text(),'Save')])";

    public static final String CANCEL = "(//button[contains(text(),'Cancel')])";

    public static final String SEARCH = "(//button[contains(text(),'Search')])";

    private static final Logger LOG =
            Logger.getLogger(TestBase.class.getName());


    public RemoteWebDriver getDriver() throws MalformedURLException {

        RemoteWebDriver driver = null;
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("firefox");

        System.setProperty("http.proxyHost", "www-proxy.us.oracle.com");
        System.setProperty("http.proxyPort", "80");
        System.setProperty("https.proxyHost", "www-proxy.us.oracle.com");
        System.setProperty("https.proxyPort", "80");

        String PROXY = System.getProperty("http.proxyHost") + ":"
                + System.getProperty("http.proxyPort");

        Proxy proxy = new Proxy();
        proxy.setHttpProxy(PROXY).setFtpProxy(PROXY).setSslProxy(PROXY);
        capabilities.setCapability(CapabilityType.PROXY, proxy);

        String seHost = System.getProperty("selenium.host");

        String sePort = System.getProperty("selenium.port");
        URL server =
                new URL("http://" + seHost + ":" + sePort + "/wd/hub");
        driver = new RemoteWebDriver(server, capabilities);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        return driver;
    } public String getPassword() {
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
    
    public void setNonAdminUserName(String nonAdminUserName) {
        this.nonAdminUserName = nonAdminUserName;
    }

    public String getNonAdminUserName() {
        return nonAdminUserName;
    }

    public void setNonAdminPassword(String nonAdminPassword) {
        this.nonAdminPassword = nonAdminPassword;
    }

    public String getNonAdminPassword() {
        return nonAdminPassword;
    }


    /**
     * Capture current browser page to fileName in the ./target subdirectory
     * 
     * @param se
     * @param fileName 
     */
    public static synchronized void capturePage(WebDriver se, String fileName) {
        File file = new File("");

        WebDriver augmentedDriver = new Augmenter().augment(se);

        TakesScreenshot screenShot = (TakesScreenshot) augmentedDriver;
        String base64Screenshot = screenShot.getScreenshotAs(OutputType.BASE64);
        byte[] decodedScreenshot =
                Base64.decodeBase64(base64Screenshot.getBytes());

        fileName = file.getAbsolutePath() + "/target/" + fileName;

        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            fos.write(decodedScreenshot);
            fos.close();
        } catch (IOException ex) {
            LOG.severe("Cannot create screenshot: " + ex);
        }
    }


  


    void login(WebDriver se) {
        se.get(getServiceURL());

        String user = getUserName();
        String pass = getPassword();

        se.findElement(By.id("gwt-debug-username")).clear();
        se.findElement(By.id("gwt-debug-username")).sendKeys(user);
        se.findElement(By.id("gwt-debug-password")).clear();
        se.findElement(By.id("gwt-debug-password")).sendKeys(pass);
        se.findElement(By.id("gwt-debug-signin-button")).click();
    }
    
    void loginAsNonAdmin(WebDriver se) {
        se.get(getServiceURL());

        String user = getNonAdminUserName();
        String pass = getNonAdminPassword();

        se.findElement(By.id("gwt-debug-username")).clear();
        se.findElement(By.id("gwt-debug-username")).sendKeys(user);
        se.findElement(By.id("gwt-debug-password")).clear();
        se.findElement(By.id("gwt-debug-password")).sendKeys(pass);
        se.findElement(By.id("gwt-debug-signin-button")).click();
    }


    void afterMethod(WebDriver se, ITestResult result) {

        String testName = result.getTestClass().getRealClass().getSimpleName()
                + "::" + result.getName();

        if (!result.isSuccess()) {
            capturePage(se, testName + "-fail.png");
            LOG.info(testName + " Failed");
            Reporter.log(testName + " Failed");
        } else {
            LOG.info(testName + " Passed");
            Reporter.log(testName + " Passed");
        }
    }


 
}
