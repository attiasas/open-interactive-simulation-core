package org.attias.open.interactive.simulation.core.backend.utils;

import org.apache.commons.lang3.SystemUtils;
import org.attias.open.interactive.simulation.core.backend.config.ProjectConfiguration;
import org.attias.open.interactive.simulation.core.utils.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProjectUtils {
    // Shortcut for Open Interactive Simulation
    public static final String OIS = "ois";

    // The valid dimensions of the icons for desktop
    public static final Set<Integer> DESKTOP_ICON_SIZES = new HashSet<>(List.of(/*16,*/32,128));
    // The valid extensions of the icons
    public enum IconExtension {
        PNG (".png"),
        ICO (".ico"),
        ICNS (".icns");

        public final String value;

        IconExtension(String value) {
            this.value = value;
        }
    }

    public static String getDesktopIconResourcePath(IconExtension extension, int dim) {
        return OIS + "/icons/" + getDesktopIconFileName(extension, dim);
    }

    public static String getDesktopIconFileName(IconExtension extension, int dim) {
        return "icon" + dim + extension.value;
    }

    public enum DesktopOS {
        Windows, Linux, Mac, Unknown
    }

    public static DesktopOS getCurrentOS() {
        String osName = SystemUtils.OS_NAME.toLowerCase();
        if (osName == null) {
            return DesktopOS.Unknown;
        }
        if (osName.contains("darwin") || osName.contains("mac")) {
            return DesktopOS.Mac;
        }
        if (osName.contains("windows")) {
            return DesktopOS.Windows;
        }
        if (osName.contains("linux")) {
            return DesktopOS.Linux;
        }
        return DesktopOS.Unknown;
    }

    public static ProjectConfiguration getProjectConfigurationFromEnvironment() throws IOException {
        String configurationPath = System.getenv(ProjectConfiguration.ENV_PROJECT_CONFIG_PATH);
        if (configurationPath == null) {
            return null;
        }
        return IOUtils.getObjFromJsonFile(Paths.get(configurationPath).toFile(), ProjectConfiguration.class);
    }

    public static String getProjectConfigurationsAssetsPath() {
        // for resources root dir just input the file name, separator is always /
        return OIS + "/" + ProjectConfiguration.DEFAULT_FILE_NAME;
    }

    public static ProjectConfiguration getProjectConfigurationFromResources(InputStream in) throws IOException {
        if (in == null) {
            throw new RuntimeException("Can't find project configurations.");
        }
        return IOUtils.getObjFromJsonFile(in, ProjectConfiguration.class);
    }

    public static ProjectConfiguration getProjectConfiguration(InputStream assetsProjectConfiguration) throws IOException {
        ProjectConfiguration configuration = getProjectConfigurationFromEnvironment();
        if (configuration != null) {
            // Dynamic running
            return configuration;
        }
        // Deployed mode
        return getProjectConfigurationFromResources(assetsProjectConfiguration);
    }
}
