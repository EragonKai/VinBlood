package com.kai.vinblood.display;

import com.kai.vinblood.util.Bounds;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Kai on Mar 21, 2019
 */
public abstract class VButton extends HUDComponent implements Clickable, Hoverable{
    private boolean hovered;

    public VButton(Bounds bounds, BufferedImage image) {
        super(bounds, image);

        HUDController.getInstance().addHoverable(this);
        HUDController.getInstance().addClickable(this);
    }

    public abstract void drawOnHover(Graphics g);

    @Override
    public void update() { }

    @Override
    public boolean checkCollision(int otherX, int otherY) {
        return checkCollisionWithMouse(otherX, otherY);
    }

    @Override
    public void onHover() {
        hovered = true;
    }

    @Override
    public void onNotHovered() {
        hovered = false;
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        if (hovered) {
            drawOnHover(g);
        }
    }
}
