package org.attias.open.interactive.simulation.core.backend.engine;

import org.attias.open.interactive.simulation.core.backend.config.ProjectConfiguration;
import org.attias.open.interactive.simulation.core.backend.utils.ProjectUtils;

import java.io.IOException;

/**
 * For specific platform to run on the engine
 */
public class AppConfiguration {

    public static final String ENV_PROJECT_JAR = "IOS_ENV_PROJECT_JAR_PATH";

    public enum AppType {
        Desktop
    }

    private AppType type;

    private ProjectConfiguration projectConfiguration;

    private String projectJarPath;

    public ProjectConfiguration getProjectConfiguration() {
        return projectConfiguration;
    }

    public AppConfiguration setProjectConfiguration(ProjectConfiguration projectConfiguration) {
        this.projectConfiguration = projectConfiguration;
        return this;
    }

    public String getProjectJarPath() {
        return projectJarPath;
    }

    public AppConfiguration setProjectJarPath(String projectJarPath) {
        this.projectJarPath = projectJarPath;
        return this;
    }

    public AppType getType() {
        return type;
    }

    public AppConfiguration setType(AppType type) {
        this.type = type;
        return this;
    }

    public static AppConfiguration getFromEnvironment() throws IOException {
        System.out.println("Loading Application Configuration");
        return new AppConfiguration().setProjectJarPath(System.getenv(ENV_PROJECT_JAR)).setProjectConfiguration(ProjectUtils.getFromEnvironment());
    }
}
