package com.kai.vinblood.levels;

import com.kai.vinblood.core.Game;
import com.kai.vinblood.core.ResourceManager;
import com.kai.vinblood.display.Display;
import com.kai.vinblood.display.HUDController;
import com.kai.vinblood.objs.GameObject;
import com.kai.vinblood.objs.items.*;
import com.kai.vinblood.util.Bounds;
import com.kai.vinblood.util.Globals;
import com.kai.vinblood.util.ID;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author Kai on Mar 22, 2019
 */
public class LootInstanceGenerator {

    private static final Random rand = new Random();

    public static void generateLoot(Bounds bounds, Map<ID, Double> potentialDrops) {
        List<Item> contents = new ArrayList<>();
        boolean rare = false;
        boolean legendary = false;

        for (ID id: potentialDrops.keySet()) {
            double chance = potentialDrops.get(id);
            if (rand.nextDouble() < chance) {
                Item item = ItemLoader.getItem(id);
                contents.add(item);
                if (item.getRarity() == Rarity.rare) {
                    rare = true;
                }
                if (item.getRarity() == Rarity.legendary) {
                    legendary = true;
                }
            }
        }

        while(!contents.isEmpty()) {
            List<Item> aContents = new ArrayList<>();
            List<Item> toRemove = new ArrayList<>();
            for (int i = 0; i < 8; i++) {
                if (contents.size()-1 >= i) {
                    aContents.add(contents.get(i));
                    toRemove.add(contents.get(i));
                }
            }


            if (contents.size() > 0) {
                if (legendary) {
                    new LootInstance(bounds, aContents, ResourceManager.getImage("legendarylootbag"));
                } else if (rare) {
                    new LootInstance(bounds, aContents, ResourceManager.getImage("rarelootbag"));
                } else {
                    new LootInstance(bounds, aContents);
                }
            }

            contents.removeAll(toRemove);
        }
    }

    public static class LootInstance extends GameObject {
        private final BufferedImage contentsImage = ResourceManager.getImage("lootdropcontents");
        private List<Item> contents;

        boolean contentsVisible = false;

        public LootInstance(Bounds bounds, List<Item> contents) {
            this(bounds, contents, ResourceManager.getImage("lootbag"));
        }

        public LootInstance(Bounds bounds, List<Item> contents, BufferedImage image) {
            super(bounds, image);
            getBounds().width = 16;
            getBounds().height = 16;

            this.contents = new ArrayList<>();
            for (Item i: contents) {
                //TODO: Do this a better way:
                if (i instanceof Weapon) {
                    this.contents.add(new Weapon(Game.getPlayer(), Globals.INVIS_BOUNDS, (Weapon)i));
                } else if (i instanceof Rune) {
                    this.contents.add(new Rune(Game.getPlayer(), Globals.INVIS_BOUNDS, (Rune)i));
                } else if (i instanceof Rust) {
                    this.contents.add(new Rust(Game.getPlayer(), Globals.INVIS_BOUNDS, (Rust)i));
                }
            }

        }

        @Override
        public void update() {
            if (contents.size() == 0) {
                die();
                System.out.println("yep");
            }

            contents.removeIf((item) -> item == null);
        }

        @Override
        public void draw(Graphics g) {
            super.draw(g);
            if (checkCollision(Game.getPlayer())) {
                g.drawImage(contentsImage, Bounds.scaledWidthNumber(960), Bounds.scaledHeightNumber(626), null);
                showContents();
            } else {
                hideContents();
            }
        }

        public void showContents() {
            if (!contentsVisible) {

                int incrementX = 35;
                int placeX = 960 - incrementX;
                int incrementY = 35;
                int placeY = 626;

                for (int i = 0; i < contents.size(); i++) {
                    if (i != 5) {
                        placeX += incrementX;
                    } else {
                        placeX = 960;
                        placeY += incrementY;
                    }

                    Item item = contents.get(i);
                    item.setVisible(true);
                    item.getBounds().x = placeX+2;
                    item.getBounds().y = placeY+2;
                }

                contentsVisible = true;
            }
        }

        public void hideContents() {
            if (contentsVisible) {
                for (Item i: contents) {
                    i.setVisible(false);
                    i.getBounds().x = -500;
                    i.getBounds().y = -500;
                }
                contentsVisible = false;
            }
        }
    }

}
