package org.attias.open.interactive.simulation.core.config;

import org.attias.open.interactive.simulation.core.backend.engine.AppConfiguration;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RunnerConfiguration {

    // The version (tag) of the ois runner that will be used (the engine version)
    public String version = "1.0-SNAPSHOT";
    // List of all the platforms that the project will run on, must have at least one entry
    public Set<AppConfiguration.AppType> types = new HashSet<>();
}
