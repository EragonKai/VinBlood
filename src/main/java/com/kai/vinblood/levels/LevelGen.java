package com.kai.vinblood.levels;

import com.kai.vinblood.core.Game;
import com.kai.vinblood.display.Display;
import com.kai.vinblood.util.Bounds;
import com.kai.vinblood.util.ID;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kai on Mar 21, 2019
 */
public class LevelGen {
    private static final int MIN_DISTANCE_TO_PLAYER = 500;

    //In the future, this will return 3 lists of enemies that the player will select one of.
    static List<ID> generateEnemies(int levelNum) {
        List<ID> enemies = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            enemies.add(new ID(0, "Goblin"));
        }

        return enemies;
    }

    static Bounds getBoundsAwayFromPlayer() {
        Bounds bounds = new Bounds(0, 0);

        do {
            bounds.x = (int)(Math.random() * Display.DISPLAY_WIDTH);
            bounds.y = (int)(Math.random() * Display.DISPLAY_HEIGHT);
        } while (bounds.distanceTo(Game.getPlayer().getCenterX(), Game.getPlayer().getCenterY()) > MIN_DISTANCE_TO_PLAYER);

        return bounds;
    }
}
