package org.attias.open.interactive.simulation.core;

import org.attias.open.interactive.simulation.core.backend.engine.InteractiveSimulationEngine;
import org.attias.open.interactive.simulation.core.files.ResourceManager;

/**
 * Static object used to access in states
 */
public class OIS {
    // The engine running the simulation
    public static InteractiveSimulationEngine engine;
    // The resources manager of the simulation
    public static ResourceManager resources;
}
