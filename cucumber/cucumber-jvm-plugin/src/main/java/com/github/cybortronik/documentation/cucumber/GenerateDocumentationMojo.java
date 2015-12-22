package com.github.cybortronik.documentation.cucumber;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by stanislav on 12/14/15.
 */
@Mojo(name = "document")
public class GenerateDocumentationMojo extends AbstractMojo {

    @Parameter(defaultValue = "target/site/documentation")
    private File path;

    @Parameter
    private File pathToAbstract;

    public void execute() throws MojoExecutionException, MojoFailureException {
        assertOnNull(path, "Please specify the `path` parameter");
        getLog().info("Processing documentation on: " + path);
        try {
            FileUtils.forceMkdir(path);

            URL reportPath = GenerateDocumentationMojo.class.getResource("/html/");
            assertOnNull(reportPath, "Did not found report resources.");

            getLog().info("Copy data from " + reportPath);
            com.github.cybortronik.documentation.cucumber.FileUtils.copyResourcesRecursively(reportPath, path);
            if (pathToAbstract != null) {
                getLog().info("Found file that contains `Abstract`: " + pathToAbstract);
                FileUtils.copyFile(pathToAbstract, new File(path.toString() + "/abstract.txt"));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void assertOnNull(Object obj, String message) {
        if (obj == null)
            throw new IllegalArgumentException(message);
    }
}
