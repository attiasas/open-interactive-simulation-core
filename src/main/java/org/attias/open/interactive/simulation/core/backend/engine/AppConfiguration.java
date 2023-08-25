package org.attias.open.interactive.simulation.core.backend.engine;

import org.attias.open.interactive.simulation.core.backend.config.ProjectConfiguration;
import org.attias.open.interactive.simulation.core.backend.utils.ProjectUtils;

import java.io.IOException;

/**
 * For specific platform to run on the engine
 */
public class AppConfiguration {
    // The environment variable used to tell the runner where the OIS project jar exists.
    // It will load the jar to run with the engine
    public static final String ENV_PROJECT_JAR = "OIS_ENV_PROJECT_JAR_PATH";
    // The environment variable used to tell the runner where the OIS project resources directory.
    // When running not in production, it will load the resource to run with the engine as the assets directory
    public static final String ENV_PROJECT_ASSETS_DIR = "OIS_ENV_PROJECT_ASSETS_DIR";
    // The environment variable used to tell the runner what is the name of the artifact to generate on publish
    public static final String ENV_PROJECT_NAME = "OIS_ENV_PROJECT_NAME";

    // The Supported application running platforms by the OIS
    public enum AppType {
        Desktop
    }

    private AppType type;

    private ProjectConfiguration projectConfiguration;

    public ProjectConfiguration getProjectConfiguration() {
        return projectConfiguration;
    }

    public AppConfiguration setProjectConfiguration(ProjectConfiguration projectConfiguration) {
        this.projectConfiguration = projectConfiguration;
        return this;
    }

    public AppType getType() {
        return type;
    }

    public AppConfiguration setType(AppType type) {
        this.type = type;
        return this;
    }

    public static AppConfiguration getRunnerConfigurations() throws IOException {
        AppConfiguration configuration = new AppConfiguration();
        configuration.setProjectConfiguration(ProjectUtils.getProjectConfiguration());
        return configuration;
    }
}
