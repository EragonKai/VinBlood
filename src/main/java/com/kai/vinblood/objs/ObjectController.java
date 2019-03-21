package com.kai.vinblood.objs;

import com.kai.vinblood.core.Updatable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ObjectController implements Updatable {
    private static ObjectController instance;
    public static ObjectController getInstance() {
        if (instance == null) instance = new ObjectController();
        return instance;
    }

    private List<GameObject> gameObjects;
    private List<GameObject> toAdd;
    private Iterator<GameObject> iterator;

    private ObjectController() {
        gameObjects = new ArrayList<>();
        toAdd = new ArrayList<>();
    }

    @Override
    public void update() {
        for (iterator = gameObjects.iterator(); iterator.hasNext(); ) {
            GameObject object = iterator.next();
            object.update();

            if(object.isMarkedForRemoval()) {
                iterator.remove();
            }
        }

        gameObjects.addAll(toAdd);
        toAdd.clear();

    }

    public void add(GameObject object) {
        toAdd.add(object);
    }

    public List<GameObject> getGameObjects() {
        return new ArrayList<>(gameObjects);
    }

}
