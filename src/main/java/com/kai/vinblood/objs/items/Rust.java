package com.kai.vinblood.objs.items;

import com.kai.vinblood.objs.entities.Entity;
import com.kai.vinblood.util.Bounds;
import com.kai.vinblood.util.ID;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * @author Kai on Mar 20, 2019
 */
public class Rust extends Item {

    public Rust(Entity owner, Bounds bounds, Rust base) {
        super(owner, bounds, base);
    }

    public Rust(BufferedImage image, List<ItemBehavior> behaviors, ID id) {
        super(image, behaviors, id);
    }
}
