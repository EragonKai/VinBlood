package com.kai.vinblood.objs.items;

import com.kai.vinblood.objs.GameObject;
import com.kai.vinblood.objs.entities.Entity;

import java.util.List;

/**
 * @author Kai on Mar 20, 2019
 */
public interface ActiveItemBehavior extends ItemBehavior {
    void apply(Entity owner, List<GameObject> scene);

}
