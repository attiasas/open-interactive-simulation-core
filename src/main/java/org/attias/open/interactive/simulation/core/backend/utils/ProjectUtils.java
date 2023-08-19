package org.attias.open.interactive.simulation.core.backend.utils;

import org.attias.open.interactive.simulation.core.backend.config.ProjectConfiguration;
import org.attias.open.interactive.simulation.core.state.StateManager;
import org.attias.open.interactive.simulation.core.utils.IOUtils;

import java.io.IOException;
import java.nio.file.Paths;

public class ProjectUtils {

    public static String getInitialStateClassName(ProjectConfiguration configuration) {
        if (configuration.states.isEmpty()) {
            throw new RuntimeException("You must configure at least one state");
        }
        if (configuration.initialState.isBlank()) {
            throw new RuntimeException("You must configure a valid initial state key");
        }
        return configuration.states.get(configuration.initialState);
    }

    public static ProjectConfiguration getFromEnvironment() throws IOException {
        String configurationPath = System.getenv(ProjectConfiguration.ENV_PROJECT_CONFIG_PATH);
        System.out.println("Loading Project Configuration from: " + configurationPath);
        return IOUtils.getObjFromJsonFile(Paths.get(configurationPath).toFile(), ProjectConfiguration.class);
    }
}
