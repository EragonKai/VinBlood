package com.kai.vinblood.objs;

import com.kai.vinblood.core.ResourceManager;
import com.kai.vinblood.core.Updatable;
import com.kai.vinblood.util.Bounds;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject implements Updatable {
    private Bounds bounds;

    private boolean visible = true;
    private BufferedImage image;

    private boolean markedForRemoval = false;

    public GameObject(Bounds bounds, BufferedImage image) {
        this.image = image;
        this.bounds = bounds;

        if (bounds != null) {
            ObjectController.getInstance().add(this);
        }
    }

    public boolean checkCollision(GameObject otherObject) {
        return ((otherObject.getX() < getX()+getWidth()) &&
                (otherObject.getX()+otherObject.getWidth() > getX()) &&
                (otherObject.getY() < getY()+getHeight()) &&
                (otherObject.getY()+otherObject.getHeight() > getY()));
    }

    public double distanceTo (int tX, int tY) {
        double a = Math.abs( (getCenterX()) - tX);
        double b = Math.abs( (getCenterY()) - tY);
        return(Math.sqrt((a*a) + (b*b)));
    }

    public double distanceTo( GameObject otherObject) {
        return distanceTo(otherObject.getCenterX(), otherObject.getCenterY());
    }

    public void draw(Graphics g) {
        g.drawImage(image, bounds.scaledX(), bounds.scaledY(), null);
    }

    public void resizeImage() {
        image = ResourceManager.toBufferedImage(image.getScaledInstance(bounds.scaledWidth(), bounds.scaledHeight(), Image.SCALE_FAST));
    }

    public int getX() { return bounds.x; }
    public int getY() { return bounds.y; }
    public int getWidth() { return bounds.width; }
    public int getHeight() { return bounds.height; }
    public int getCenterX() { return (getX() + getWidth()/2); }
    public int getCenterY() { return (getY() + getHeight()/2); }
    public void setX(int x) { bounds.x = x; }
    public void setY(int y) { bounds.y = y; }
    public void setImage(BufferedImage image) { this.image = image; }
    public BufferedImage getImage() { return image; }
    public Bounds getBounds() { return bounds; }

    public void setBounds(Bounds bounds) {
        this.bounds = bounds;
    }

    public boolean isVisible() {
        return visible;
    }
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void die() {
        markedForRemoval = true;
    }

    public boolean isMarkedForRemoval() {
        return markedForRemoval;
    }
}
