package com.kai.vinblood.levels;

import com.kai.vinblood.core.Game;
import com.kai.vinblood.core.Updatable;

/**
 * @author Kai on Mar 21, 2019
 */
public class LevelController implements Updatable {
    private static LevelController instance;
    private Level currentLevel;

    public static void start() {
        instance = new LevelController();
    }

    private LevelController() {
        currentLevel = new Level(Game.getPlayer().getLevel());
    }

    @Override
    public void update() {
        if (currentLevel.isOver()) {
            nextLevel();
        }
        currentLevel.update();
    }

    private void nextLevel() {
        Game.getPlayer().setLevel(Game.getPlayer().getLevel() + 1);
        currentLevel = new Level(Game.getPlayer().getLevel());
    }

    public static LevelController getInstance() {
        if (instance == null) {
            start();
        }
        return instance;
    }
}
