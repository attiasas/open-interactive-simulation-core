package org.attias.open.interactive.simulation.core.config;

import org.attias.open.interactive.simulation.core.utils.IOUtils;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class ProjectConfiguration {
    public static final String ENV_PROJECT_CONFIG_PATH = "IOS_ENV_PROJECT_CONFIG_PATH";
    public static final String ProjectConfigFileName = "simulation.ois";

    // Name of the project
    public String name = "OIS simulation";
    // The initial state key
    public String initialState;
    // State key -> class name of IState implementation, must have at least one entry
    public Map<String, String> states = new Hashtable<>();
    // List of all the project asset directories, can be empty
    public Set<String> assetsDirectories = new HashSet<>();
    // Configuration on what platform this project will run on
    public RunnerConfiguration runner = new RunnerConfiguration();

    public String getInitialStateClassName() {
        if (states.isEmpty()) {
            throw new RuntimeException("You must configure at least one state");
        }
        if (initialState.isBlank()) {
            throw new RuntimeException("You must configure a valid initial state key");
        }
        return states.get(initialState);
    }

    public static ProjectConfiguration getFromEnvironment() throws IOException {
        String configurationPath = System.getenv(ENV_PROJECT_CONFIG_PATH);
        System.out.println("Loading Project Configuration from: " + configurationPath);
        return IOUtils.getObjFromJsonFile(Paths.get(configurationPath), ProjectConfiguration.class);
    }
}
