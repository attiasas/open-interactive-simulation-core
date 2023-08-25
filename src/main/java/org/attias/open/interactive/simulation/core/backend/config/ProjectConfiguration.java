package org.attias.open.interactive.simulation.core.backend.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.attias.open.interactive.simulation.core.utils.IOUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

/**
 * The project configurations object that holds the values from simulation.ois config file
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectConfiguration {
    // The environment variable that lets the runner know where the OIS project config file exists
    // This is used for running OIS project dynamically not on production
    public static final String ENV_PROJECT_CONFIG_PATH = "OIS_ENV_PROJECT_CONFIG_PATH";
    // THe default name of the project configuration file that will be searched at the root project
    public static final String DEFAULT_FILE_NAME = "simulation.ois";

    // Title of the project that will be shown on the window
    public String title;
    // The initial state key that will be used when initializing the engine
    public String initialState;
    // State key -> class name of IState implementation, must have at least one entry
    public Map<String, String> states = new Hashtable<>();

    // Configuration on what platform this project will run on
    public PublishConfiguration publish = new PublishConfiguration();

    /**
     * Get a project configuration object with the values that are stored in the given path to
     * the Json configuration file
     * @param path - path to the Json file
     * @return the project configuration
     * @throws IOException - on reading the file
     */
    public static ProjectConfiguration get(Path path) throws IOException {
        File file = path.toFile();
        if (file.isDirectory()) {
            return IOUtils.getObjFromJsonFile(path.resolve(DEFAULT_FILE_NAME).toFile(), ProjectConfiguration.class);
        } else {
            return IOUtils.getObjFromJsonFile(file, ProjectConfiguration.class);
        }
    }
}
