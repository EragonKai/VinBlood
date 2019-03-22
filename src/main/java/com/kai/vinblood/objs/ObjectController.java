package com.kai.vinblood.objs;

import com.kai.vinblood.core.Game;
import com.kai.vinblood.core.Updatable;
import com.kai.vinblood.display.Display;
import com.kai.vinblood.levels.LevelController;
import com.kai.vinblood.objs.entities.enemies.Enemy;

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

    private boolean updateScreen = false;
    @Override
    public void update() {
        for (iterator = gameObjects.iterator(); iterator.hasNext(); ) {
            GameObject object = iterator.next();
            object.update();

            if(object.isMarkedForRemoval()) {
                if (object instanceof Enemy) {
                    LevelController.getInstance().getCurrentLevel().enemyDied();
                }
                iterator.remove();
                updateScreen = true;
            }
        }

        if (toAdd.size() > 0) {
            gameObjects.addAll(toAdd);
            toAdd.clear();
            updateScreen = true;
        }

        if (updateScreen) {
            Display.getInstance().updateLayers();
            updateScreen = false;
        }

        if (Game.state == Game.GameState.RUNNING) {
            LevelController.getInstance().update();
        }

    }

    public void add(GameObject object) {
        toAdd.add(object);
    }

    public List<GameObject> getGameObjects() {
        return new ArrayList<>(gameObjects);
    }

}
