package com.kai.vinblood.display;

import com.kai.vinblood.core.Game;
import com.kai.vinblood.core.ResourceManager;
import com.kai.vinblood.objs.items.Rust;
import com.kai.vinblood.util.Bounds;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kai on Mar 21, 2019
 */
public class RustsDisplay extends HUDComponent implements Clickable {
    private static RustsDisplay instance;
    private static boolean alive = false;

    private List<Rust> rusts = new ArrayList<>();

    private static final Bounds TILE_BOUNDS = new Bounds(37, 454, 33, 35);

    public RustsDisplay() {
        super(new Bounds(0,415,495, 185), ResourceManager.getImage("inventory"));

        HUDController.getInstance().addClickable(this);
    }

    @Override
    public void update() {
        rusts = Game.getPlayer().getPlayerInventory().getEquippedRusts();
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);

        int x = TILE_BOUNDS.x;
        int y = TILE_BOUNDS.y;
        int width = TILE_BOUNDS.width;
        int height = TILE_BOUNDS.height;
        for (Rust r: rusts) {
            r.setVisible(true);
            r.setBounds(new Bounds(x, y, width, height));
            x += width;
            if (x >= 386) {
                y += height;
            }
        }
    }

    @Override
    public void onClick() {
        die();
        alive = false;
        rusts.forEach((r) -> r.setVisible(false));
    }

    @Override
    public boolean checkCollision(int otherX, int otherY) {
        return !checkCollisionWithMouse(otherX, otherY);
    }

    public static void create() {
        if (!alive) {
            alive = true;
            instance = new RustsDisplay();
        }
    }
}
