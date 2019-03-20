package com.kai.vinblood.objs.entities.enemies;

import com.kai.vinblood.objs.entities.Entity;

public enum CombatBehavior {
    STRAIGHT_CHASE {
        public void act(Enemy actor, Entity target) {
            if (actor.distanceTo(target) > actor.getBounds().width/2) {
                final int variance = 8;

                int targetX = target.getCenterX() + ((int)(Math.random() * variance * 2 - variance));
                int targetY = target.getCenterY() + ((int)(Math.random() * variance * 2 - variance));

                double deltaX = targetX - actor.getCenterX();
                double deltaY = targetY - actor.getCenterY();
                double direction = Math.atan2(deltaY, deltaX);


                actor.setX((int) (actor.getX() + (Entity.speedStatConversion(actor.getStat("swiftness").getValue()) * Math.cos(direction))));
                actor.setY((int) (actor.getY() + (Entity.speedStatConversion(actor.getStat("swiftness").getValue()) * Math.sin(direction))));
            }
        }
    };

    public abstract void act(Enemy actor, Entity target);
}
