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
import com.kai.vinblood.util.VFont;

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
    protected BufferedImage tooltip = ResourceManager.getImage("itemtooltip", 128, 128);
    protected int image_size = 128;

    public Item(Entity owner, Bounds bounds, Item base) {
        super(bounds, base.getImage());
        this.behaviors = base.getBehaviors();
        this.owner = owner;
        this.id = base.getID();

        setPhysical(false);

        resizeImage();

        HUDController.getInstance().addHoverable(this);
        setDisplayLayer(3);
    }

    @Override
    public boolean checkCollision(int otherX, int otherY) {
        return checkCollisionWithMouse(otherX, otherY);
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
            while (g.getFontMetrics().stringWidth(getID().idname) > image_size-20) {
                tooltip = ResourceManager.toBufferedImage(tooltip.getScaledInstance((int)(image_size * 1.25), (int)(image_size * 1.25), Image.SCALE_FAST));
                image_size = (int)(image_size * 1.25);
            }

            int y = getBounds().scaledY();
            int x = Bounds.scaledWidthNumber(getCenterX());
            if (getX() + image_size > Display.DISPLAY_WIDTH) {
                x -= image_size;
            }
            if (getY() + image_size > Display.DISPLAY_HEIGHT) {
                y -= image_size;
            }

            g.drawImage(tooltip, x, y, null);

            //Text:
            g.setColor(Color.white);
            g.setFont(new VFont(1.15));
            g.drawString(getID().idname, x+20, y+25);
            g.setColor(new Color(19, 30 ,53));
            g.drawLine(x+10, y+30, x+image_size-10, y+30);
            g.setFont(new VFont(1));
            int inc = g.getFontMetrics().getAscent()+5;
            int currentLineY = y+30 + inc;

            g.setColor(new Color(56, 160, 170));
            if (this instanceof Weapon) {
                Weapon weapon = (Weapon) this;
                g.drawString("damage: " + weapon.getDamage(), x+20, currentLineY);
                currentLineY += inc;
                g.drawString("firerate: " + weapon.getRateOfAttack(), x+20, currentLineY);
            } else if (this instanceof Rune) {
                Rune rune = (Rune) this;
                g.drawString(rune.getSkill().getName(), x+20, currentLineY);
            }

            g.setColor(new Color(19, 30 ,53));
            g.drawLine(x+10, currentLineY+5, x+image_size-10, currentLineY+5);
            currentLineY += inc;
            currentLineY += 5;

            g.setColor(new Color(56, 160, 170));
            for (ItemBehavior b: behaviors) {
                g.drawString(b.getDescription(), x+20, currentLineY);
                currentLineY += inc;
            }

            if (currentLineY - y > image_size-10) {
                tooltip = ResourceManager.toBufferedImage(tooltip.getScaledInstance((int)(image_size * 1.25), (int)(image_size * 1.25), Image.SCALE_FAST));
                image_size = (int)(image_size * 1.25);
            }
        }
    }

    @Override
    public String getDescription() {
        return this.toString();
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
