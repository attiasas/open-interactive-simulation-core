package org.attias.open.interactive.simulation.core.engine;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import org.attias.open.interactive.simulation.core.state.IState;
import org.attias.open.interactive.simulation.core.utils.JarLoader;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;

public class InteractiveSimulationEngine  extends ApplicationAdapter {
//    SpriteBatch batch;
//    Texture img;

    private AppConfiguration configuration;
    private IState state;

    public InteractiveSimulationEngine(AppConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void create () {
//        batch = new SpriteBatch();
//        img = new Texture("badlogic.jpg");
        try(JarLoader jarLoader = new JarLoader(this.configuration.getDynamicJarPath())) {
            state = jarLoader.newInstance(this.configuration.getStateClassName());
        } catch (IOException | ClassNotFoundException | InvocationTargetException | InstantiationException |
                 IllegalAccessException | NoSuchMethodException e) {
            System.out.println("Can't init on: " + this.configuration.getDynamicJarPath() + " " + this.configuration.getStateClassName());
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
