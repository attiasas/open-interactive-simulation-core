package org.attias.open.interactive.simulation.core.log;

public interface ILogger {

    enum Level {
        Debug, Info, Warn, Error
    }

    void debug(String message);
    void info(String message);
    void warn(String message);
    void error(String message);

    void error(String message, Throwable exception);
}
