package org.attias.open.interactive.simulation.core.backend.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectConfiguration {
    public static final String ENV_PROJECT_CONFIG_PATH = "IOS_ENV_PROJECT_CONFIG_PATH";

    // Name of the project
    public String name = "OIS simulation";
    // The initial state key
    public String initialState = "";
    // State key -> class name of IState implementation, must have at least one entry
    public Map<String, String> states = new Hashtable<>();

    // Configuration on what platform this project will run on
    public RunnerConfiguration runner = new RunnerConfiguration();
}
