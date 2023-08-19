package org.attias.open.interactive.simulation.core.state;

public interface IState {

    boolean enter(Object... parameters);
    void exit();

    void pause();
    void resume();

    void resize(int width, int height);
    void render();
    boolean update(float dt);

    void dispose();
}
