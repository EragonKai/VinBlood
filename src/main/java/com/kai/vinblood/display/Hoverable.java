package com.kai.vinblood.display;

/**
 * @author Kai on Mar 20, 2019
 */
public interface Hoverable {
    void onHover();
    void onNotHovered();
    boolean checkCollision(int otherX, int otherY);
}
