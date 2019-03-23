package com.kai.vinblood.objs.items;

import com.kai.vinblood.objs.entities.Entity;
import com.kai.vinblood.objs.entities.Projectile;
import com.kai.vinblood.objs.entities.player.Player;
import com.kai.vinblood.util.Bounds;
import com.kai.vinblood.util.Globals;
import com.kai.vinblood.util.ID;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;

/**
 * @author Kai on Mar 20, 2019
 */
public class Weapon extends Item {
    private HashMap<ID, Projectile> projectiles;
    private int damage = 5;
    private double rateOfAttack = 2;
    private ID projectileToShoot = new ID(0);

    private int shotTick = 0;

    public Weapon(Entity owner, Bounds bounds, Weapon base) {
        super(owner, bounds, base);
        this.projectiles = new HashMap<>(base.getProjectiles());
        this.damage = base.getDamage();
        this.rateOfAttack = base.getRateOfAttack();

        setType("Weapon");
    }

    public Weapon(BufferedImage image, List<ItemBehavior> behaviors, HashMap<ID, Projectile> projectiles, ID id) {
        super(image, behaviors, id);
        this.projectiles = projectiles;
    }

    public void shoot(int targetX, int targetY) {
        if (shotTick > (Globals.FRAMES_PER_SECOND / (rateOfAttack))) {
            Projectile p = new Projectile(new Bounds(owner.getCenterX(), owner.getCenterY()), getProjectile(projectileToShoot), targetX, targetY);
            if (owner instanceof Player) {
                p.setOwnedByPlayer(true);
            }
            shotTick = 0;
        }
    }

    public Projectile getProjectile(ID id) {
        Projectile p = projectiles.get(id);
        p.setDamage(this.damage);
        return p;
    }

    @Override
    public void update() {
        super.update();
        if (!(shotTick > (Globals.FRAMES_PER_SECOND / (rateOfAttack)))) {
            shotTick++;
        }
    }

    public HashMap<ID, Projectile> getProjectiles() {
        return projectiles;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setRateOfAttack(double rateOfAttack) {
        this.rateOfAttack = rateOfAttack;
    }

    public int getDamage() {
        return damage;
    }

    public double getRateOfAttack() {
        return rateOfAttack;
    }

    public ID getProjectileToShoot() {
        return projectileToShoot;
    }

    public void setProjectileToShoot(ID projectileToShoot) {
        this.projectileToShoot = projectileToShoot;
    }
}
