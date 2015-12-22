package com.github.cybortronik.documentation.cucumber;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
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
        URL reportPath = GenerateDocumentationMojo.class.getResource("html/");
        try {
            FileUtils.copyDirectory(new File(reportPath.toURI()), path);
            if (pathToAbstract != null) {
                FileUtils.copyFile(pathToAbstract, new File(path.toString() + "/abstract.txt"));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
