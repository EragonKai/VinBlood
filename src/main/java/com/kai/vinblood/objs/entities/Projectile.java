package com.kai.vinblood.objs.entities;

import com.kai.vinblood.core.Game;
import com.kai.vinblood.display.Display;
import com.kai.vinblood.objs.GameObject;
import com.kai.vinblood.objs.ObjectController;
import com.kai.vinblood.objs.entities.enemies.Enemy;
import com.kai.vinblood.util.Bounds;

import java.awt.image.BufferedImage;

//TODO: These ain't resizing correctly.

public class Projectile extends GameObject {
    private int speed = 25;
    //Basically accuracy
    private int variance = 30;
    private int damage = 5;
    private int range = 25;
    private int targetX, targetY;
    private boolean ownedByPlayer = false;

    private double distanceTraveled = 0;

    public Projectile(Bounds bounds, Projectile base, int targetX, int targetY) {
        super(bounds, base.getImage());
        getBounds().width = base.getWidth();
        getBounds().height = base.getHeight();

        speed = base.getSpeed();
        variance = base.getVariance();
        damage = base.getDamage();
        range = base.getRange();

        this.targetX = targetX;
        this.targetY = targetY;

        this.targetX += ((int)(Math.random() * variance * 2 - variance));
        this.targetY += ((int)(Math.random() * variance * 2 - variance));

        updateTarget();
    }

    public Projectile(BufferedImage img) {
        super(null, img);
    }

    //TODO: Manually setting the target to off screen seems like a bad idea.
    public void updateTarget() {
        double m = (((double) (targetY - getY())) / ((double) (targetX - getX())));
        int b = (int) (getY() - (m * getX()));
        if (targetX > getX()) {
            targetX = getCloseNumber(1.2, Display.DISPLAY_HEIGHT, targetX);
            targetY = (int) ((m * targetX) + b);
        } else if (targetX < getX()) {
            targetX -= (getCloseNumber(1.2, Display.DISPLAY_WIDTH, targetX));
            targetY = (int) ((m * targetX) + b);
        } else {
            if (targetY > getY()) {
                targetY =  Display.DISPLAY_HEIGHT;
            } else if (targetY < getY()) {
                targetY = 0-getHeight();
            }
        }
    }
    private int getCloseNumber(double multiAmount, int bound, int start) {
        if (start == 0) {
            return start;
        }

        if (start < 0) {
            start *= -1;
        }

        double num = start;
        do {
            num *= multiAmount;
        } while (num < bound);
        return (int)num;
    }

    @Override
    public void update() {
        double deltaX = targetX - getCenterX();
        double deltaY = targetY - getCenterY();
        double direction = Math.atan2(deltaY, deltaX);

        double xChange = Math.abs(Entity.speedStatConversion(speed) * Math.cos(direction));
        double yChange = Math.abs(Entity.speedStatConversion(speed) * Math.sin(direction));
        distanceTraveled = distanceTraveled + Math.sqrt(xChange*xChange + yChange*yChange);

        setX((int) (getX() + (Entity.speedStatConversion(speed) * Math.cos(direction))));
        setY((int) (getY() + (Entity.speedStatConversion(speed) * Math.sin(direction))));

        if (!ownedByPlayer) {
            if (checkCollision(Game.getPlayer())) {
                Game.getPlayer().takeDamage(damage);
                die();
            }
        } else {
            for (GameObject o: ObjectController.getInstance().getGameObjects()) {
                if (o instanceof Enemy) {
                    if (checkCollision(o)) {
                        ((Entity)o).takeDamage(Game.getPlayer().getDamage());
                        die();
                        break;
                    }
                }
            }
        }

        if (distanceTraveled > 20 * range) {
            die();
        }
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setVariance(int variance) {
        this.variance = variance;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getSpeed() {
        return speed;
    }

    public int getVariance() {
        return variance;
    }

    public int getDamage() {
        return damage;
    }

    public void setOwnedByPlayer(boolean ownedByPlayer) {
        this.ownedByPlayer = ownedByPlayer;
    }

    @Override
    public String toString() {
        return "Projectile{" +
                "speed=" + speed +
                ", variance=" + variance +
                ", damage=" + damage +
                '}';
    }
}
