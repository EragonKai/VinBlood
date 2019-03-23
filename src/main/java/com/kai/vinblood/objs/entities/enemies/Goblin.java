package com.kai.vinblood.objs.entities.enemies;

import com.kai.vinblood.core.Game;
import com.kai.vinblood.objs.entities.enemies.behaviors.CombatBehavior;
import com.kai.vinblood.util.Bounds;
import com.kai.vinblood.util.ID;

public class Goblin extends Enemy {

    public Goblin(Bounds bounds) {
        super(bounds, EnemyLoader.getEnemy(new ID(((Math.random() > 0.5) ? 0000 : 0001), "Goblin")));
    }

    @Override
    public void update() {
        super.update();
        CombatBehavior.STRAIGHT_CHASE.act(this, Game.getPlayer());
    }

    @Override
    protected void attack() {
        super.attack();
        if (id.idnum == 1) {
            shootProjectileAtPlayer(new ID(0));
        }
    }
}
