package org.attias.open.interactive.simulation.core.backend.engine;

import org.attias.open.interactive.simulation.core.backend.config.ProjectConfiguration;
import org.attias.open.interactive.simulation.core.backend.utils.ProjectUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * For specific platform to run on the engine
 */
@SuppressWarnings("unused")
public class AppConfiguration {
    // The environment variable, if exists, used to tell the runner that it's running in debug mode and not published
    public static final String ENV_DEBUG_MODE = "OIS_ENV_DEBUG_MODE";
    // The environment variable used to tell the runner where the OIS project jar exists.
    // It will load the jar to run with the engine
    public static final String ENV_PROJECT_JAR = "OIS_ENV_PROJECT_JAR_PATH";
    // The environment variable used to tell the runner where the OIS project resources directory.
    // When running not in production, it will load the resource to run with the engine as the assets directory
    public static final String ENV_PROJECT_ASSETS_DIR = "OIS_ENV_PROJECT_ASSETS_DIR";
    // The environment variable used to tell the runner what is the name of the artifact to generate on publish
    public static final String ENV_PROJECT_NAME = "OIS_ENV_PROJECT_NAME";
    // The environment variable used to tell the runner what is the group name of the artifact to generate on publish
    public static final String ENV_PROJECT_GROUP = "OIS_ENV_PROJECT_GROUP";
    // The environment variable used to tell the runner what is the version of the artifact to generate on publish
    public static final String ENV_PROJECT_VERSION = "OIS_ENV_PROJECT_VERSION";
    // The environment variable used to tell the runner what is the version number (typically incremented with each release) of the artifact to generate on publish
    public static final String ENV_PROJECT_VERSION_NUMBER = "OIS_ENV_PROJECT_VERSION_NUM";
    // The environment variable used when running not on publish, to tell the runner where the android sdk
    // If not exists, it will search the value from environment variable named 'ANDROID_HOME';
    public static final String ENV_ANDROID_SDK_PATH = "OIS_ENV_ANDROID_SDK_PATH";

    // The Supported application running platforms by the OIS
    public enum AppType {
        Desktop, Android
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

    public static AppConfiguration getRunnerConfigurations(InputStream assetsProjectConfiguration) throws IOException {
        return new AppConfiguration().setProjectConfiguration(ProjectUtils.getProjectConfiguration(assetsProjectConfiguration));
    }
}
