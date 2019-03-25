package com.kai.vinblood.display;

import com.kai.vinblood.core.Game;
import com.kai.vinblood.core.ResourceManager;
import com.kai.vinblood.objs.items.Item;
import com.kai.vinblood.objs.items.Rune;
import com.kai.vinblood.objs.items.Rust;
import com.kai.vinblood.objs.items.Weapon;
import com.kai.vinblood.util.Bounds;
import com.kai.vinblood.util.VFont;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.ArrayList;
public class ItemContextMenu extends HUDComponent implements Clickable {

    private static final BufferedImage buttonImage = ResourceManager.getImage("itemcontextmenu");
    private List<VButton> buttons;
    private Item selectedItem;

    public ItemContextMenu(Bounds bounds, Item item) {
        super(bounds, null);
        setDisplayLayer(Display.Layer.CONTEXT.getLayer());
        selectedItem = item;
        HUDController.getInstance().addClickable(this);

        buttons = new ArrayList<>();

        if (!item.isEquipped()) {
            Bounds equipBounds = new Bounds(getBounds().x, getBounds().y, 50, 15);
            buttons.add(new VButton(equipBounds, buttonImage) {
                @Override
                public void drawOnHover(Graphics g) {
                }

                @Override
                public void draw(Graphics g) {
                    super.draw(g);
                    g.setColor(new Color(32, 49, 84));
                    g.setFont(new VFont(1.2));
                    g.drawString("Equip", getBounds().scaledX()+Bounds.scaledWidthNumber(3), getBounds().scaledY()+ Bounds.scaledHeightNumber(15));
                }

                @Override
                public void onClick() {
                    switch(item.getType()) {
                        case "Weapon":
                            selectedItem = Game.getPlayer().getPlayerInventory().equipWeapon((Weapon)selectedItem);
                            break;
                        case "Rune":
                            selectedItem = Game.getPlayer().getPlayerInventory().equipRune((Rune)selectedItem);
                            break;
                        case "Rust":
                            Game.getPlayer().getPlayerInventory().equipRust((Rust)selectedItem);
                            break;
                    }

                    ItemContextMenu.this.die();
                }
            });
        }

        buttons.forEach((button) -> button.setDisplayLayer(Display.Layer.CONTEXT.getLayer()));
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics g) {

    }

    @Override
    public void onClick() {
        die();
    }

    @Override
    public void die() {
        super.die();
        for (VButton b: buttons) {
            b.die();
        }
    }

    @Override
    public boolean checkCollision(int otherX, int otherY) {
        return true;
    }
}
