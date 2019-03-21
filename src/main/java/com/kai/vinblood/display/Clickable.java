package com.kai.vinblood.display;

/**
 * @author Kai on Mar 20, 2019
 */
public interface Clickable {
    void onClick();
    boolean checkCollision(int otherX, int otherY);
}
