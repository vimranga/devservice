package com.oracle.qa.devsvc;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.logging.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;


/**
 *
 * @author ADF QA
 */
@Test(groups = {SignInPage.GROUP, "UI"})
public class SignInPage extends TestBase {

    public static final String GROUP = "signIn";

    private RemoteWebDriver se;

    private static final Logger LOG =
            Logger.getLogger(SignInPage.class.getName());


    @BeforeClass
    public void beforeClass() throws MalformedURLException {
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


}
