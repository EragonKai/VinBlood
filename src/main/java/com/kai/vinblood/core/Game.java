package com.kai.vinblood.core;

import com.kai.vinblood.display.Display;
import com.kai.vinblood.display.PlayerInfoDisplay;
import com.kai.vinblood.net.ClientConnection;
import com.kai.vinblood.objs.ObjectController;
import com.kai.vinblood.objs.entities.enemies.Goblin;
import com.kai.vinblood.objs.entities.player.Player;
import com.kai.vinblood.util.Bounds;
import com.kai.vinblood.util.Globals;

public class Game implements Updatable {
    public enum GameState{MENU, SETTINGS, RUNNING, DEATH};
    static GameState state = GameState.MENU;

    private static String playerName;
    private static Player player;


    public Game(String playerName) {
        this.playerName = playerName;

        transitionToScene(GameState.RUNNING);

        loop();
    }

    public void transitionToScene(GameState newState) {
        player = null;
        ObjectController.getInstance().getGameObjects().clear();

        switch(newState) {
            case MENU:

                break;
            case SETTINGS:

                break;
            case RUNNING:
                player = new ClientConnection().getPlayer(playerName);
                new PlayerInfoDisplay();

                //Temporary:
                player.getBounds().x = 550;
                player.getBounds().y = 550;

                for (int i = 0; i < 5; i++) {
                    new Goblin(new Bounds((int)(Math.random() * 1100), (int)(Math.random() * 700)));
                }
                break;
            case DEATH:

                break;
        }

        state = newState;
    }

    @Override
    public void update() {
        Display.getInstance().update();
        ObjectController.getInstance().update();
    }

    private void loop() {
        while(true) {
            update();

            try {
                Thread.sleep((int)(1000.0/Globals.FRAMES_PER_SECOND));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static Player getPlayer() {
        return player;
    }
}
