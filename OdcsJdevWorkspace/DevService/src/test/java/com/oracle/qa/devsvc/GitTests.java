package com.oracle.qa.devsvc;

import java.io.File;
import java.util.logging.Logger;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.testng.annotations.Test;


/**
 *
 * @author ADF QA
 */
@Test(groups = {GitTests.GROUP})
public class GitTests extends TestBase {

    public static final String GROUP = "git";

    private static final Logger LOG =
            Logger.getLogger(GitTests.class.getName());


    public void cleanup() throws Exception {
        File dir = new File("/tmp/git");

        if (dir.exists()) {
            LOG.info("Removing: " + dir.getAbsolutePath());
            Runtime.getRuntime().exec("rm -rf "
                    + dir.getAbsolutePath()).waitFor();
        }

    }


    @Test(dependsOnMethods = {"cleanup"})
    public void cloneRepository() throws Exception {

        File dir = new File("/tmp/git");

        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        Repository repository = builder.setGitDir(dir).build();

        Git git = new Git(repository);
        CloneCommand clone = git.cloneRepository();

        clone.setBare(false);
        clone.setCloneAllBranches(true);

        String url = "http://qa-dev.developer.us.oracle.com/s/"
                + "qa-dev_jingsteam/scm/qa-dev_jingsteam.git";

        String login = "lyle.harris@oracle.com";
        String password = "Welcome1";

        clone.setDirectory(dir).setURI(url);
        UsernamePasswordCredentialsProvider user =
                new UsernamePasswordCredentialsProvider(login, password);
        clone.setCredentialsProvider(user);
        clone.call();

    }


}
