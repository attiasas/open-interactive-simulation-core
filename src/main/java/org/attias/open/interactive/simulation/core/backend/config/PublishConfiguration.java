package org.attias.open.interactive.simulation.core.backend.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.attias.open.interactive.simulation.core.backend.engine.AppConfiguration;

import java.util.HashSet;
import java.util.Set;

/**
 * The object that holds the information needed for publishing the OIS project
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PublishConfiguration {

    // List of all the platforms that the project will run on, must have at least one entry
    public Set<AppConfiguration.AppType> platforms = new HashSet<>();
    // [Optional] name for the execution [default: project name]
    public String publishedName = "";
    // [Optional] the directory that the custom icon stored at
    public String iconsDir;
    // [Optional] if exists and true will try to generate missing icons
    public Boolean generateMissingIcons;
}
