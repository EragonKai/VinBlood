package com.kai.vinblood.core;

import com.kai.vinblood.display.HUDController;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Input implements KeyListener, MouseListener, Updatable {
    private boolean mouseHeld;
    private int mouseX, mouseY;

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseHeld = true;
    }

    public void mouseExists(int x, int y) {
        mouseX = (int) (MouseInfo.getPointerInfo().getLocation().getX() - x);
        mouseY = (int) (MouseInfo.getPointerInfo().getLocation().getY() - y);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseHeld = false;
        HUDController.getInstance().doClicks(e.getX(), e.getY());

        switch (Game.state) {
            case MENU:

                break;
            case SETTINGS:

                break;
            case RUNNING:
                Game.getPlayer().shoot(e.getX(), e.getY());
                break;
            case DEATH:

                break;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (Game.state) {
            case MENU:

                break;
            case SETTINGS:

                break;
            case RUNNING:
                Game.getPlayer().getPlayerInput().keyPressed(e.getKeyChar());
                break;
            case DEATH:

                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (Game.state) {
            case MENU:

                break;
            case SETTINGS:

                break;
            case RUNNING:
                Game.getPlayer().getPlayerInput().keyReleased(e.getKeyChar());
                break;
            case DEATH:

                break;
        }
    }

    @Override
    public void update() {
        if (Game.state == Game.GameState.RUNNING) {
            if (mouseHeld) {
                Game.getPlayer().shoot(mouseX, mouseY);
            }
        }

        if (!mouseHeld) {
            HUDController.getInstance().doHovers(mouseX, mouseY);
        }
    }
}
