package org.attias.open.interactive.simulation.core.backend.utils;

import org.attias.open.interactive.simulation.core.backend.config.ProjectConfiguration;
import org.attias.open.interactive.simulation.core.utils.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

public class ProjectUtils {
    // Shortcut for Open Interactive Simulation
    public static final String OIS = "ois";

    // the name of the plugin directory at home user and resource directory at project
    public static final String OIS_DIRECTORY_NAME = "." + OIS;
    // The valid dimensions of the icons
    public static final int[] ICON_SIZES = new int[]{16,32,128};
    // The valid extensions of the icons
    public static final String[] ICON_EXTENSIONS = new String[] {".png", ".ico", ".icns"};

    public static ProjectConfiguration getProjectConfigurationFromEnvironment() throws IOException {
        String configurationPath = System.getenv(ProjectConfiguration.ENV_PROJECT_CONFIG_PATH);
        if (configurationPath == null) {
            return null;
        }
        return IOUtils.getObjFromJsonFile(Paths.get(configurationPath).toFile(), ProjectConfiguration.class);
    }

    public static ProjectConfiguration getProjectConfigurationFromResources() throws IOException {
        // for resources root dir just input the file name, separator is always /
        String path = OIS_DIRECTORY_NAME + "/" + ProjectConfiguration.DEFAULT_FILE_NAME;
        try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(path)) {
            if (in == null) {
                throw new RuntimeException("Can't find project configurations at " + path);
            }
            return IOUtils.getObjFromJsonFile(in, ProjectConfiguration.class);
        }
    }

    public static ProjectConfiguration getProjectConfiguration() throws IOException {
        ProjectConfiguration configuration = getProjectConfigurationFromEnvironment();
        if (configuration != null) {
            // Dynamic running
            return configuration;
        }
        // Deployed mode
        return getProjectConfigurationFromResources();
    }
}
