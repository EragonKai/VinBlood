package com.kai.vinblood.objs.items;

import com.kai.vinblood.objs.entities.Entity;
import com.kai.vinblood.objs.entities.player.skills.Skill;
import com.kai.vinblood.util.Bounds;
import com.kai.vinblood.util.ID;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * @author Kai on Mar 20, 2019
 */
public class Rune extends Item {
    private Skill skill;

    public Rune(Entity owner, Bounds bounds, Rune base) {
        super(owner, bounds, base);
        this.skill = base.getSkill();
    }

    public Rune(BufferedImage image, List<ItemBehavior> behaviors, Skill skill, ID id) {
        super(image, behaviors, id);
        this.skill = skill;
    }

    public Skill getSkill() {
        return skill;
    }
}
