package com.kai.vinblood.objs.items;

import com.kai.vinblood.core.ResourceManager;
import com.kai.vinblood.display.Display;
import com.kai.vinblood.display.HUDController;
import com.kai.vinblood.display.Hoverable;
import com.kai.vinblood.objs.GameObject;
import com.kai.vinblood.objs.ObjectController;
import com.kai.vinblood.objs.entities.Entity;
import com.kai.vinblood.util.Bounds;
import com.kai.vinblood.util.ID;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kai on Mar 20, 2019
 */
public abstract class Item extends GameObject implements ItemBehavior, Hoverable {
    private List<ItemBehavior> behaviors;
    protected Entity owner;
    private ID id;

    protected boolean hoveredOver = false;
    protected final static BufferedImage tooltip = ResourceManager.getImage("itemtooltip", 128, 128);
    protected final int image_size = 128;

    public Item(Entity owner, Bounds bounds, Item base) {
        super(bounds, base.getImage());
        this.behaviors = base.getBehaviors();
        this.owner = owner;
        this.id = base.getID();

        setPhysical(false);

        getBounds().width = 24;
        getBounds().height = 24;
        resizeImage();

        HUDController.getInstance().addHoverable(this);
    }

    public Item(BufferedImage image, List<ItemBehavior> behaviors, ID id) {
        super(null, image);
        this.behaviors = behaviors;
        this.id = id;
    }

    @Override
    public void update() {
        for (ItemBehavior b: behaviors) {
            if (b instanceof ActiveItemBehavior) {
                ((ActiveItemBehavior) b).apply(owner, ObjectController.getInstance().getGameObjects());
            }
        }
    }

    @Override
    public void onEquip(Entity owner) {
        behaviors.forEach((b) -> b.onEquip(owner));
    }

    @Override
    public void onUnEquip(Entity owner) {
        behaviors.forEach((b) -> b.onUnEquip(owner));
    }

    public List<TargetedItemBehavior> getTargeted() {
        List<TargetedItemBehavior> targetedItemBehaviors = new ArrayList<>();
        behaviors.forEach((b) -> {
            if (b instanceof TargetedItemBehavior) {
                targetedItemBehaviors.add((TargetedItemBehavior)b);
            }
        });
        return targetedItemBehaviors;
    }

    public ID getID() {
        return id;
    }

    public List<ItemBehavior> getBehaviors() {
        return behaviors;
    }

    @Override
    public void onHover() {
        hoveredOver = true;
    }

    @Override
    public void onNotHovered() {
        hoveredOver = false;
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        if (hoveredOver) {
            int y = getCenterY();
            int x = getCenterX();
            if (getX() + image_size > Display.DISPLAY_WIDTH) {
                x -= image_size;
            }
            if (getY() + image_size > Display.DISPLAY_HEIGHT) {
                y -= image_size;
            }

            g.drawImage(tooltip, x, y, null);
        }
    }

    @Override
    public String toString() {
        return "Item{" +
                "behaviors=" + behaviors +
                ", owner=" + owner +
                ", id=" + id +
                '}';
    }
}
