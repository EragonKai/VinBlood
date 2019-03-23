package com.kai.vinblood.objs.entities.enemies.behaviors;

import com.kai.vinblood.objs.GameObject;
import com.kai.vinblood.objs.entities.enemies.Enemy;
import com.kai.vinblood.objs.entities.player.Player;

import java.util.List;

public enum SceneBehavior {
    TEMP {
        public void act(Enemy actor, Player player, List<GameObject> scene) { }
    };

    public abstract void act(Enemy actor, Player player, List<GameObject> scene);
}
