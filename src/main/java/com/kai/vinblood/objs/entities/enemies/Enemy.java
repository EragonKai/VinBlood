package com.kai.vinblood.objs.entities.enemies;

import com.kai.vinblood.core.Game;
import com.kai.vinblood.objs.entities.Entity;
import com.kai.vinblood.objs.entities.Projectile;
import com.kai.vinblood.util.Bounds;
import com.kai.vinblood.util.Globals;
import com.kai.vinblood.util.ID;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Enemy extends Entity {
    private Map<ID, Projectile> projectiles;
    private boolean active = true;
    int attackTick;
    public ID id;

    //Used when parsing the XML as a model Enemy
    public Enemy(BufferedImage image) {
        super(null, image);
        projectiles = new HashMap<>();
        active = false;

        statInit();
    }

    //Used when creating an Enemy for use
    public Enemy(Bounds bounds, Enemy e) {
        super(bounds, e.getImage());

        getBounds().width = e.getBounds().width;
        getBounds().height = e.getBounds().height;

        statInit();

        for (String stat: e.statManager.getStats().keySet()) {
            if (!statManager.getStats().containsKey(stat)) {
                statManager.addStat(e.getStat(stat).getName(), e.getStat(stat).getDesc());
            }
            statManager.setStat(stat, e.getStat(stat).baseValue);
            statManager.incStat(stat, e.getStat(stat).positiveChange);
            statManager.decStat(stat, e.getStat(stat).negativeChange);
        }

        this.id = e.id;

        projectiles = new HashMap<>(e.getProjectiles());
        attackTick = 10000;
    }

    private void statInit() {
        statManager.addStat("damage", "Base damage dealt by an enemy.", 10);
        statManager.addStat("rate of attack", "Frequency at which projectiles are fired/damage is dealt.", 1);
    }

    @Override
    public void update() {
        super.update();
        if (active) {
            attackTick++;
            if (attackTick > (Globals.FRAMES_PER_SECOND / getStat("rate of attack").getValue())) {
                attack();
                attackTick = 0;
            }
        }
    }

    protected void attack() {
        if (checkCollision(Game.getPlayer())) {
            Game.getPlayer().takeDamage(getDamage());
        }
    }

    protected void shootProjectileAtPlayer(ID id) {
        new Projectile(new Bounds(getCenterX(), getCenterY()), projectiles.get(id), Game.getPlayer().getCenterX(), Game.getPlayer().getCenterY());
    }


    @Override
    public void draw(Graphics g) {
        super.draw(g);
        g.setColor(Color.RED);
        g.fillRect(getBounds().scaledX(), getBounds().scaledY() - Bounds.scaledHeightNumber(10),
                (int)(((getStat("health").getValue() / (double)(getStat("max health").getValue()))) * getBounds().scaledWidth()), Bounds.scaledHeightNumber(5));
    }

    public Map<ID, Projectile> getProjectiles() {
        return projectiles;
    }

    @Override
    public String toString() {
        return "Enemy{" +
                "projectiles=" + projectiles +
                ", statManager=" + statManager +
                '}';
    }
}
