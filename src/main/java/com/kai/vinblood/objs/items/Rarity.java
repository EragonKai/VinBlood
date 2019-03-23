package com.kai.vinblood.objs.items;

import java.awt.*;

/**
 * @author Kai on Mar 22, 2019
 */
public enum Rarity {
    common(new Color(56, 160, 170)), uncommon(Color.GREEN), rare(Color.orange), legendary(Color.MAGENTA);

    private Color color;

    Rarity(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }}
