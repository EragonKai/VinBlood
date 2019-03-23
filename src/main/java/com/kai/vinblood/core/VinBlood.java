package com.kai.vinblood.core;

import com.kai.vinblood.display.Display;
import com.kai.vinblood.objs.entities.enemies.EnemyLoader;
import com.kai.vinblood.objs.items.ItemLoader;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class VinBlood {

    public static void main(String[] args) {
        String playerName = "";
        while(playerName.trim().equals("")) {
            playerName = JOptionPane.showInputDialog("Enter username: ");
        }


        if (playerName != null) {
            JFrame frame = new JFrame("VinBlood");
            frame.add(Display.getInstance());
            frame.pack();
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);

            try {
                EnemyLoader.loadXMLS();
                ItemLoader.loadXMLS();
            } catch (ParserConfigurationException | IOException | SAXException e) { e.printStackTrace(); }

            new Game(playerName);
        }

    }

}
