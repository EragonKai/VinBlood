package com.kai.vinblood.objs.entities.player;

import com.kai.vinblood.core.Updatable;
import com.kai.vinblood.objs.items.*;
import com.kai.vinblood.util.Bounds;
import com.kai.vinblood.util.ID;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kai on Mar 20, 2019
 */
public class PlayerInventory implements Updatable {
    private Player owner;

    private Weapon equippedWeapon;
    private Rune equippedRune;
    private List<Rust> equippedRusts;

    private List<Item> spareInventory;

    private static final Bounds EQUIP_WEP_BOUNDS = new Bounds(20, 640, 32, 32);
    private static final Bounds EQUIP_RUNE_BOUNDS = new Bounds(60, 640, 32, 32);
    private static final Bounds INVIS_BOUNDS = new Bounds(-500, -500, 32, 32);

    public PlayerInventory(Player owner) {
        this.owner = owner;

        equippedRusts = new ArrayList<>();
        spareInventory = new ArrayList<>();

        equipWeapon(ItemLoader.getWeapon(new ID(0)));
        equipRune(ItemLoader.getRune(new ID(1)));
    }

    @Override
    public void update() {
        equippedWeapon.update();
        equippedRune.update();
        equippedRusts.forEach((r) -> update());
    }

    public Weapon equipWeapon(Weapon newWeapon) {
        Weapon oldWeapon = null;
        if (equippedWeapon != null) {
            oldWeapon = equippedWeapon;
            oldWeapon.onUnEquip(owner);
        }
        equippedWeapon = new Weapon(owner, new Bounds(EQUIP_WEP_BOUNDS), newWeapon);
        newWeapon.onEquip(owner);
        return oldWeapon;
    }

    public Rune equipRune(Rune newRune) {
        Rune oldRune = null;
        if (equippedRune != null) {
            oldRune = equippedRune;
            oldRune.onUnEquip(owner);
        }
        equippedRune = new Rune(owner, new Bounds(EQUIP_RUNE_BOUNDS), newRune);
        newRune.onEquip(owner);
        return oldRune;
    }

    public void equipRust(Rust newRust) {
        Rust rust = new Rust(owner, new Bounds(INVIS_BOUNDS), newRust);
        rust.setVisible(false);
        equippedRusts.add(rust);
    }

    public void addToInventory(Item item) {
        spareInventory.add(item);
    }

    public Weapon getEquippedWeapon() {
        return equippedWeapon;
    }

    public Rune getEquippedRune() {
        return equippedRune;
    }

    public List<Rust> getEquippedRusts() {
        return equippedRusts;
    }
}
