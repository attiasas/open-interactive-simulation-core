package org.attias.open.interactive.simulation.core.tools;

import org.attias.open.interactive.simulation.core.log.AppLog;
import org.attias.open.interactive.simulation.core.state.StateManager;

public class Timer {

    private static AppLog log = AppLog.get(Timer.class);
    public float target;
    public float elapsed;

    public boolean loop;

    public Timer() {
        this(Float.MAX_VALUE);
    }

    public Timer(float target) {
        this.target = target;
    }

    public float timeLeftToTarget() {
        return target - elapsed;
    }

    public boolean isOver() {
        return elapsed >= target;
    }

    public void reset() {
        this.elapsed = 0;
    }

    public float getTarget() {
        return target;
    }

    public void setTarget(float target) {
        this.target = target;
    }

    public boolean isLoop() {
        return loop;
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    public void tic(float deltaTime) {
        if (isOver() && !loop) {
            return;
        }
        elapsed += deltaTime;
        if (isOver() && loop) {
            elapsed -= target;
        }
        log.info("Tic [isOver " + isOver() + "] [elapsed " + elapsed + ", target " + target + "]");
    }
}
