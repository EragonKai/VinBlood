package com.kai.vinblood.objs.items;

import com.kai.vinblood.objs.entities.Entity;

/**
 * @author Kai on Mar 20, 2019
 */
public interface ItemBehavior {
    void onEquip(Entity owner);
    void onUnEquip(Entity owner);
    String getDescription();
}
