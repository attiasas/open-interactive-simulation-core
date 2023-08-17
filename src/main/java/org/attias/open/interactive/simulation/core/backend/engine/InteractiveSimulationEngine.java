package org.attias.open.interactive.simulation.core.backend.engine;

import com.badlogic.gdx.ApplicationAdapter;
import org.attias.open.interactive.simulation.core.config.ProjectConfiguration;
import org.attias.open.interactive.simulation.core.state.IState;
import org.attias.open.interactive.simulation.core.utils.IOUtils;
import org.attias.open.interactive.simulation.core.utils.JarLoader;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;

public class InteractiveSimulationEngine  extends ApplicationAdapter {
//    SpriteBatch batch;
//    Texture img;

    private AppConfiguration configuration;
    private JarLoader projectJar;
    private IState state;

    public InteractiveSimulationEngine(AppConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void create () {
//        batch = new SpriteBatch();
//        img = new Texture("badlogic.jpg");

        try {
            System.out.println("Loading Project Jar from: " + this.configuration.getProjectJarPath());
            this.projectJar = new JarLoader(this.configuration.getProjectJarPath());
            String stateClassName = this.configuration.getProjectConfiguration().getInitialStateClassName();
            System.out.println("Loading State [" + this.configuration.getProjectConfiguration().initialState + "]: " + stateClassName);
            this.state = this.projectJar.newInstance(stateClassName);
        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException | InvocationTargetException e) {
            System.err.println("Can't initialize OIS engine");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void render () {
        this.state.render();
//        ScreenUtils.clear(1, 0, 0, 1);
//        batch.begin();
//        batch.draw(img, 0, 0);
//        batch.end();
    }

    @Override
    public void dispose () {
//        batch.dispose();
//        img.dispose();
    }
}
