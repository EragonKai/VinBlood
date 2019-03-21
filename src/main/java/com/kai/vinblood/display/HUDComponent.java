package com.kai.vinblood.display;

import com.kai.vinblood.objs.GameObject;
import com.kai.vinblood.util.Bounds;

import java.awt.image.BufferedImage;

public abstract class HUDComponent extends GameObject {

    public HUDComponent(Bounds bounds, BufferedImage image) {
        super(bounds, image);
        setPhysical(false);
    }

}
