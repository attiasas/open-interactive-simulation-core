package org.attias.open.interactive.simulation.core.files;

import com.badlogic.gdx.Gdx;

import java.io.File;

public class ResourceManager {

    public File getFile(String path) {
        return Gdx.files.internal(path).file();
    }

}
