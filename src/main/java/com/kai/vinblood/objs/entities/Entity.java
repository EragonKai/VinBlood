package com.kai.vinblood.objs.entities;

import com.kai.vinblood.objs.GameObject;
import com.kai.vinblood.util.Bounds;

import java.awt.image.BufferedImage;

public abstract class Entity extends GameObject {
    public StatManager statManager;


    public Entity(Bounds bounds, BufferedImage image) {
        super(bounds, image);

        statManager = new StatManager();
        statManager.addStat("swiftness", "The speed at which a character moves.", 20);
        statManager.addStat("defense", "Reduces the amount of damage taken.", 0);
        statManager.addStat("bloodthirst", "Increases the amount of damage dealt.", 0);
        statManager.addStat("health", "The amount of life you have.", 100);
        statManager.addStat("max health", "The maximum amount of life you can have.", 100);
        statManager.addStat("mana", "The resource used to cast abilities.", 100);
        statManager.addStat("max mana", " The maximum amount of mana you can have.", 100);
    }

    public StatManager.Stat getStat(String name) {
        return statManager.getStat(name);
    }

    //Turns the speed stat into the pixel movement
    public static int speedStatConversion(int speed) {
        return (speed)/5;
    }

    @Override
    public void update() {
        if (statManager.getStat("health").getValue() <= 0) {
            die();
        }
    }

    public int getDamage() {
        return (int)(getStat("damage").getValue() * (1.00 + (getStat("bloodthirst").getValue() * 2)));
    }

    public void takeDamage(int amount) {
        int realAmount = (int)(amount - (getStat("defense").getValue() / 5.0));
        statManager.decStat("health", realAmount);
    }
}

