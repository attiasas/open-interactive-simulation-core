package org.attias.open.interactive.simulation.core.backend.utils;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ProjectJar implements Closeable {

    private URL jarUrl;
    private JarFile jarFile;
    private URLClassLoader classLoader;

    public ProjectJar(String  jarPath) throws IOException, ClassNotFoundException {
        this.jarUrl = new URL("jar:file:" + jarPath+"!/"); //new URL("file://" + jarPath);
        this.classLoader = URLClassLoader.newInstance(new URL[]{this.jarUrl}); //new URLClassLoader(, this.getClass().getClassLoader());

        this.jarFile = new JarFile(new File(jarPath));
        System.out.println("Init jar file: " + jarPath);
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry je = entries.nextElement();
            if(je.isDirectory() || !je.getName().endsWith(".class")){
                continue;
            }
            // -6 because of .class
            String className = je.getName().substring(0,je.getName().length()-6);
            className = className.replace('/', '.');
            System.out.println("found class entry: " + className);
            Class c = this.classLoader.loadClass(className);
            System.out.println("Loaded class: " + c.getName());
        }
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
