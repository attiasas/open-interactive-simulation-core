package org.attias.open.interactive.simulation.core.backend.engine;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import org.attias.open.interactive.simulation.core.OIS;
import org.attias.open.interactive.simulation.core.backend.config.ProjectConfiguration;
import org.attias.open.interactive.simulation.core.backend.utils.ReflectionUtils;
import org.attias.open.interactive.simulation.core.log.AppLog;
import org.attias.open.interactive.simulation.core.state.StateManager;
import org.attias.open.interactive.simulation.core.files.ResourceManager;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * The engine that runs the OIS projects on all platforms.
 */
public class InteractiveSimulationEngine  extends ApplicationAdapter {

    private static final AppLog log = AppLog.get(InteractiveSimulationEngine.class);
    // The Gdx application
    private Application app;
    // The application configurations with the dynamic project configurations that runs on the engine
    private final AppConfiguration configuration;
    // The object used to access resources from your OIS project
    private final ResourceManager resourceManager;
    // The state manager that handles the active states in the simulation
    public final StateManager stateManager;

    public InteractiveSimulationEngine(AppConfiguration configuration) {
        this.configuration = configuration;
        this.stateManager = new StateManager();
        this.resourceManager = new ResourceManager();
    }

    @Override
    public void create () {
        this.app = Gdx.app;
        try {
            loadFromProjectConfigurations();

            OIS.engine = this;
            OIS.resources = this.resourceManager;
        } catch (Exception e) {
            log.error("Can't initialize OIS engine");
            throw new RuntimeException(e);
        }
    }

    private void loadFromProjectConfigurations() throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        log.info("Loading Project Configurations");
        ProjectConfiguration projectConfiguration = this.configuration.getProjectConfiguration();
        log.info("Loading Project states to manager");
        for (Map.Entry<String, String> entry : projectConfiguration.states.entrySet()) {
            log.info("Loading State '" + entry.getValue() + "'");
            this.stateManager.addState(entry.getKey(), ReflectionUtils.newInstance(entry.getValue()));
        }
        log.info("Starting engine with state '" + projectConfiguration.initialState + "'");
        this.stateManager.start(projectConfiguration.initialState);
    }

    @Override
    public void render () {
        try {
            float dt = Gdx.graphics.getDeltaTime();
            if (stateManager.update(dt)) {
                stateManager.render();
            }
        } catch (Exception e) {
            handleProgramException(e);
        }
    }

    public void stop() {
        app.exit();
    }

    @Override
    public void dispose () {
        log.info("Disposing Engine");
        this.stateManager.dispose();
    }

    @Override
    public void pause() {
        try {
            log.info("Pausing Engine");
            this.stateManager.pause();
        } catch (Exception e) {
            handleProgramException(e);
        }
    }

    @Override
    public void resume() {
        try {
            log.info("Resuming Engine");
            this.stateManager.resume();
        } catch (Exception e) {
            handleProgramException(e);
        }
    }

    @Override
    public void resize(int width, int height) {
        try {
            this.stateManager.resize(width, height);
        } catch (Exception e) {
            handleProgramException(e);
        }
    }

    public int getAppWidth()
    {
        return Gdx.graphics.getWidth();
    }

    public int getAppHeight()
    {
        return Gdx.graphics.getHeight();
    }

    private void handleProgramException(Exception exception) {
        exception.printStackTrace();
        stop();
    }
}
