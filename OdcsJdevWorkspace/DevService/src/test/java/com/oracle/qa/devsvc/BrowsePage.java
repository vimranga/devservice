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
@Test(groups = {BrowsePage.GROUP, "UI"},
singleThreaded = true)
public class BrowsePage extends TestBase {

    public static final String GROUP = "projectBrowse";

    private WebDriver se;

    private static final Logger LOG =
            Logger.getLogger(BrowsePage.class.getName());

    private String gitHttpRepo;

    private File cloneDir;

    private String commitMessage;


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
        se.findElement(By.linkText("Browse")).click();
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

        if (cloneDir != null && cloneDir.exists()) {
            LOG.info("Removing: " + cloneDir.getAbsolutePath());
            Runtime.getRuntime().exec("rm -rf "
                    + cloneDir.getAbsolutePath()).waitFor();
        }
    }


    public void gitClone() throws Exception {

        WebElement leftDiv = se.findElement(By.className("left"));
        System.out.println("" + leftDiv.getText());

        List<WebElement> divs =
                leftDiv.findElements(By.tagName("div"));


        for (WebElement div : divs) {
            String text = div.getText();
            if (text.startsWith("http://") && text.endsWith(".git")) {
                gitHttpRepo = text;
                LOG.info("Git HTTP Repository: " + gitHttpRepo);
            }
        }

        assertNotNull(gitHttpRepo, "Git HTTP Repository not found");

        File tmp = new File("/tmp");
        cloneDir = File.createTempFile("TestNG-", ".git", tmp);
        LOG.info("Clone Dir: " + cloneDir.getAbsolutePath());
        assertTrue(cloneDir.delete(), "Couldn't delete Temp file");
        assertTrue(cloneDir.mkdir(), "Couldn't create Temp directory");

        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        Repository repository = builder.setGitDir(cloneDir).build();

        Git git = new Git(repository);
        CloneCommand clone = git.cloneRepository();

        clone.setBare(false);
        clone.setCloneAllBranches(true);

        String userName = getUserName();
        String password = getPassword();

        assertNotNull(userName, "Username Not Set");
        assertNotNull(password, "Password Not Set");

        clone.setDirectory(cloneDir).setURI(gitHttpRepo);
        UsernamePasswordCredentialsProvider user =
                new UsernamePasswordCredentialsProvider(userName, password);
        clone.setCredentialsProvider(user);
        clone.call();

    }


    @Test(dependsOnMethods = {"gitClone"})
    public void gitCommit() throws Exception {
        LOG.info("Git HTTP Repository: " + gitHttpRepo);
        LOG.info("Clone Dir: " + cloneDir.getAbsolutePath());

        File newFile = new File(cloneDir.getAbsolutePath() + "/newFile.txt");
        FileWriter fw = new FileWriter(newFile);
        fw.write(new Date().toString());
        fw.flush();
        fw.close();

        Git git = Git.open(cloneDir);

        AddCommand add = git.add();
        LOG.info("Adding: " + newFile.getName());
        add.addFilepattern(newFile.getName()).call();

        CommitCommand commit = git.commit();
        commitMessage = "QA TestNG/JGit Commit ("
                + new Random().nextInt(1000000) + ")";
        commit.setMessage(commitMessage).call();

        String userName = getUserName();
        String password = getPassword();

        assertNotNull(userName, "Username Not Set");
        assertNotNull(password, "Password Not Set");

        UsernamePasswordCredentialsProvider user =
                new UsernamePasswordCredentialsProvider(userName, password);

        PushCommand push = git.push();
        push.setCredentialsProvider(user);
        push.call();

    }


    @Test(dependsOnMethods = {"gitCommit"})
    public void viewCommits() {
        se.findElement(By.linkText("View Commits")).click();
        se.findElement(By.linkText(commitMessage)).click();
    }


}
