package com.kai.vinblood.core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static com.kai.vinblood.core.Game.GameState;

public class Input implements KeyListener {

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

}
