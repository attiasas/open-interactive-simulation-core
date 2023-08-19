package org.attias.open.interactive.simulation.core.state;

import org.attias.open.interactive.simulation.core.log.AppLog;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class StateManager {
    private static AppLog log = AppLog.get(StateManager.class);
    // All known states Key -> state
    private Map<String, IState> states = new HashMap<>();
    // Active state stack by keys
    private Stack<String> stateStack = new Stack<>();

    public void addState(String key, IState state) {
        if (key == null || state == null) {
            throw new IllegalArgumentException("Can't add null values, key: '" + key + "' State: " + state);
        }
        if (states.containsKey(key)) {
            throw new IllegalArgumentException("State with key '" + key + "' already exists.");
        }
        log.info("Adding state '" + key + "' <" + state.getClass() + "> into the machine.");
        this.states.put(key, state);
    }

    public IState getState(String stateKey)
    {
        return states.get(stateKey);
    }

    public IState getCurrent()
    {
        if(!hasActiveState()) {
            return null;
        }
        return this.states.get(this.stateStack.peek());
    }

    public boolean hasActiveState()
    {
        return !this.stateStack.isEmpty();
    }

    public void start(String initialState) {
        if (!states.containsKey(initialState)) {
            throw new IllegalStateException("Can't find initialize state '" + initialState + "' to start the engine.");
        }
        changeState(initialState);
    }

    // Just add to top of stack without exiting existing
    public void pushSubState(String stateKey) {
        if (stateKey == null || !this.states.containsKey(stateKey)) {
            throw new IllegalArgumentException("State '" + stateKey + "' is not registered");
        }
        if (this.stateStack.contains(stateKey)) {
            throw new IllegalStateException("State '" + stateKey + "' already in stack");
        }
        if (hasActiveState()) {
            getCurrent().pause();
        }
        log.info("Starting state '" + stateKey + "'");
        this.stateStack.push(stateKey);
        getCurrent().enter();
    }

    // Exit all states in the stack and add the given
    public void changeState(String stateKey) {
        if (stateKey == null || !this.states.containsKey(stateKey)) {
            throw new IllegalArgumentException("State '" + stateKey + "' is not registered");
        }
        while (hasActiveState()) {
            exitCurrent();
        }
        pushSubState(stateKey);
    }

    public IState exitCurrent() {
        if (!hasActiveState()) {
            return null;
        }
        // Exit current
        String outKey = stateStack.peek();
        log.info("Exit current state '" + outKey + "'");
        IState out = states.get(stateStack.pop());
        out.exit();
        // Resume current if exists
        resume();
        return out;
    }

    // IState interface

    public boolean update(float delta) {
        IState current = getCurrent();
        if (current == null) {
            return false;
        }
        try {
            if (!current.update(delta)) {
                exitCurrent();
            }
        } catch (Exception e) {
            String out = this.stateStack.peek();
            if (exitCurrent() == null) {
                throw e;
            }
            log.error("[Update] Caught exception from the current state '" + out + "'", e);
        }
        return hasActiveState();
    }

    public void render()
    {
        IState current = getCurrent();
        if (current == null) {
            return;
        }
        try {
            current.render();
        } catch (Exception e) {
            String out = this.stateStack.peek();
            if (exitCurrent() == null) {
                throw e;
            }
            log.error("[Render] Caught exception from the current state '" + out + "'", e);
        }
    }

    public void pause()
    {
        IState current = getCurrent();
        if (current == null) {
            return;
        }
        try {
            log.info("Pause current state '" + this.stateStack.peek() + "'");
            current.pause();
        } catch (Exception e) {
            String out = this.stateStack.peek();
            if (exitCurrent() == null) {
                throw e;
            }
            log.error("[Pause] Caught exception from the current state '" + out + "'", e);
        }
    }

    public void resume()
    {
        IState current = getCurrent();
        if (current == null) {
            return;
        }
        try {
            log.info("Resume current state '" + this.stateStack.peek() + "'");
            current.resume();
        } catch (Exception e) {
            String out = this.stateStack.peek();
            if (exitCurrent() == null) {
                throw e;
            }
            log.error("[Resume] Caught exception from the current state '" + out + "'", e);
        }
    }

    public void resize(int width, int height)
    {
        IState current = getCurrent();
        if (current == null) {
            return;
        }
        try {
            // TODO: check effect on other states not on peek after...
            log.debug("Resize current state '" + this.stateStack.peek() + "' to [" + width + ", " + height + "]");
            current.resize(width, height);
        } catch (Exception e) {
            String out = this.stateStack.peek();
            if (exitCurrent() == null) {
                throw e;
            }
            log.error("[Resize] Caught exception from the current state '" + out + "'", e);
        }
    }

    public void dispose()
    {
        while (hasActiveState())
        {
            exitCurrent();
        }
        for (IState state : states.values())
        {
            state.dispose();
        }
    }
}
