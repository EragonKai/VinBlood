package com.kai.vinblood.objs.entities.player;

import com.kai.vinblood.core.ResourceManager;
import com.kai.vinblood.core.Updatable;
import com.kai.vinblood.objs.entities.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PlayerInput implements Updatable {
    private Player owner;

    private static final double ANIMATIONS_PER_SECOND = 4;

    private boolean movingUp, movingDown, movingRight, movingLeft;
    private boolean imageFacingRight = true;
    private BufferedImage idle1, idle2, leftIdle1, leftIdle2;
    private BufferedImage run1, run2, leftRun1, leftRun2;

    private int aTick, maxATick;

    PlayerInput(Player owner) {
        this.owner = owner;

        run1 = ResourceManager.getImage("wizzart_f_run_anim_f1", 32, 40);
        run2 = ResourceManager.getImage("wizzart_f_run_anim_f2", 32, 40);
        idle1 = owner.getImage();
        idle2 = ResourceManager.getImage("wizzard_f_idle_anim_f1", 32, 40);

        leftIdle1 = ResourceManager.mirrorImage(idle1);
        leftIdle2 = ResourceManager.mirrorImage(idle2);
        leftRun1 = ResourceManager.mirrorImage(run1);
        leftRun2 = ResourceManager.mirrorImage(run2);

        maxATick = (int)(60/ANIMATIONS_PER_SECOND);
        aTick = 0;
    }

    void resizeImage() {
        idle1 = resize(idle1);
        idle2 = resize(idle2);
        run1 = resize(run1);
        run2 = resize(run2);
        leftIdle1 = resize(leftIdle1);
        leftIdle2 = resize(leftIdle2);
        leftRun1 = resize(leftRun1);
        leftRun2 = resize(leftRun2);
    }

    private BufferedImage resize(BufferedImage img) {
        return ResourceManager.toBufferedImage(img.getScaledInstance(owner.getBounds().scaledWidth(), owner.getBounds().scaledHeight(), Image.SCALE_FAST));
    }

    public void keyPressed(char keychar) {
        switch(keychar) {
            case 'w':
                movingUp = true;
                break;
            case 's':
                movingDown = true;
                break;
            case 'a':
                movingLeft = true;
                break;
            case 'd':
                movingRight = true;
                break;
        }
    }

    public void keyReleased(char keychar) {
        switch(keychar) {
            case 'w':
                movingUp = false;
                break;
            case 's':
                movingDown = false;
                break;
            case 'a':
                movingLeft = false;
                break;
            case 'd':
                movingRight = false;
                break;
        }
    }

    @Override
    public void update() {
        if (aTick > maxATick) {
            if (!movingLeft && !movingDown && !movingRight && !movingUp) {
                if (imageFacingRight) {
                    owner.setImage((owner.getImage() == idle1) ? idle2 : idle1);
                } else {
                    owner.setImage((owner.getImage() == leftIdle1) ? leftIdle2 : leftIdle1);
                }
                aTick = 0;
            } else {
                if (imageFacingRight) {
                    owner.setImage((owner.getImage() == run1) ? run2 : run1);
                } else {
                    owner.setImage((owner.getImage() == leftRun1) ? leftRun2 : leftRun1);
                }
                aTick = 0;
            }
        }
        aTick++;

        if (movingLeft) {
            if (imageFacingRight) {
                imageFacingRight = false;
            }
            owner.setX(owner.getX() - Entity.speedStatConversion(owner.getStat("swiftness").getValue()));
        } else if (movingRight) {
            if (!imageFacingRight) {
                imageFacingRight = true;
            }
            owner.setX(owner.getX() + Entity.speedStatConversion(owner.getStat("swiftness").getValue()));
        }
        if (movingUp) {
            owner.setY(owner.getY() - Entity.speedStatConversion(owner.getStat("swiftness").getValue()));
        } else if (movingDown) {
            owner.setY(owner.getY() + Entity.speedStatConversion(owner.getStat("swiftness").getValue()));
        }
    }
}
