/*
 * Copyright 2006 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jdave.maven.report;

import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.xml.transform.TransformerException;

import org.apache.maven.artifact.handler.ArtifactHandler;
import org.apache.maven.project.MavenProject;
import org.apache.maven.reporting.AbstractMavenReport;
import org.apache.maven.reporting.MavenReportException;
import org.codehaus.doxia.site.renderer.SiteRenderer;

/**
 * @goal report
 * @execute phase="test"
 * 
 * @author Joni Freeman
 */
public class JDaveReportMojo extends AbstractMavenReport {
    /**
     * Location where generated html will be created.
     *
     * @parameter expression="${project.reporting.outputDirectory}"
     */
    protected String outputDirectory;

    /**
     * Doxia Site Renderer
     *
     * @parameter expression="${component.org.codehaus.doxia.site.renderer.SiteRenderer}"
     * @required @readonly
     */
    protected SiteRenderer siteRenderer;

    /**
     * Maven Project
     *
     * @parameter expression="${project}"
     * @required @readonly
     */
    protected MavenProject project;
    
    /**
     * Directory of raw XML files.
     *
     * @parameter expression="${project.build.directory}/jdave"
     * @required
     */
    protected File reportsDirectory;

    /**
     * Location of the Xrefs to link.
     *
     * @parameter default-value="${project.reporting.outputDirectory}/xref-test"
     */
    protected File xrefLocation;
    
    @Override
    protected void executeReport(Locale locale) throws MavenReportException {
        try {
            getSink().rawText("<iframe src=\"jdave.html\" width=\"100%\" height=\"800\" />");
            getSink().flush();
            getSink().close();
            new SpecdoxTransformer().transform("jdave.html", reportsDirectory.getAbsolutePath(), outputDirectory);
        } catch (TransformerException e) {
            throw new MavenReportException("could not create a file", e);
        }
    }
    
    public String getName(Locale locale) {
        return getBundle(locale).getString("report.jdave.name");
    }

    public String getDescription(Locale locale) {
        return getBundle(locale).getString("report.jdave.description");
    }

    @Override
    protected SiteRenderer getSiteRenderer() {
        return siteRenderer;
    }

    @Override
    protected MavenProject getProject() {
        return project;
    }

    public String getOutputName() {
        return "jdave-report";
    }

    @Override
    protected String getOutputDirectory() {
        return outputDirectory;
    }

    private ResourceBundle getBundle(Locale locale) {
        return ResourceBundle.getBundle("jdave-report", locale, getClass().getClassLoader());
    }

    @Override
    public boolean canGenerateReport() {
        ArtifactHandler artifactHandler = project.getArtifact().getArtifactHandler();
        return "java".equals(artifactHandler.getLanguage());
    }
}
