package com.kai.vinblood.objs.entities.player;

import com.kai.vinblood.core.ResourceManager;
import com.kai.vinblood.objs.entities.Entity;
import com.kai.vinblood.util.Bounds;


public class Player extends Entity {
    private PlayerInput playerInput;
    private PlayerInventory playerInventory;

    public Player() {
        super(new Bounds(30, 30, 32, 40), ResourceManager.getImage("wizzard_f_idle_anim_f0", 32, 40));
        playerInput = new PlayerInput(this);
        playerInventory = new PlayerInventory(this);
    }

    public void shoot(int targetX, int targetY) {
        playerInventory.getEquippedWeapon().shoot(targetX, targetY);
    }

    @Override
    public int getDamage() {
        return (int)(playerInventory.getEquippedWeapon().getDamage() * (1.00 + ((getStat("bloodthirst").getValue() * 2)/100.0)));
    }

    @Override
    public void update() {
        super.update();
        playerInput.update();
    }

    @Override
    public void resizeImage() {
        super.resizeImage();
        playerInput.resizeImage();
    }

    public PlayerInput getPlayerInput() {
        return playerInput;
    }
}
