package org.attias.open.interactive.simulation.core.engine;

public class AppConfiguration {

    public static final String ENV_TITLE = "IOS_ENV_TITLE";
    public static final String ENV_STATE = "IOS_ENV_STATE";
    public static final String ENV_PROJECT_JAR = "IOS_ENV_PROJECT_JAR";

    public enum AppType {
        Desktop
    }

    private AppType type;
    private String title;
    private String dynamicJarPath;

    private String stateClassName;

    public String getDynamicJarPath() {
        return dynamicJarPath;
    }

    public AppConfiguration setDynamicJarPath(String dynamicJarPath) {
        this.dynamicJarPath = dynamicJarPath;
        return this;
    }

    public String getStateClassName() {
        return stateClassName;
    }

    public AppConfiguration setStateClassName(String stateClassName) {
        this.stateClassName = stateClassName;
        return this;
    }

    public AppType getType() {
        return type;
    }

    public AppConfiguration setType(AppType type) {
        this.type = type;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public AppConfiguration setTitle(String title) {
        this.title = title;
        return this;
    }

    public static AppConfiguration getFromEnvironment() {
        return new AppConfiguration().setTitle(System.getenv(ENV_TITLE)).setDynamicJarPath(System.getenv(ENV_PROJECT_JAR)).setStateClassName(System.getenv(ENV_STATE));
    }
}
