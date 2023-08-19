package org.attias.open.interactive.simulation.core.backend.utils;

import org.attias.open.interactive.simulation.core.log.AppLog;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.jar.JarFile;

public class ProjectJar implements Closeable {
    private URL jarUrl;
    private JarFile jarFile;
    private URLClassLoader classLoader;

    public ProjectJar(String  jarPath) throws IOException, ClassNotFoundException {
        this.jarUrl = new URL("jar:file:" + jarPath+"!/");
        this.classLoader = URLClassLoader.newInstance(new URL[]{this.jarUrl});
        this.jarFile = new JarFile(new File(jarPath));
    }

    public <T extends Object> T newInstance(String classToLoad) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Class<T> loadedClass = (Class<T>) this.classLoader.loadClass(classToLoad);
        return loadedClass.getDeclaredConstructor().newInstance();
    }

    public InputStream getResourceAsStream(Path pathFromResourceFolder) {
        return this.classLoader.getResourceAsStream(pathFromResourceFolder.toString());
    }

    @Override
    public void close() throws IOException {
        this.classLoader.close();
    }
}
