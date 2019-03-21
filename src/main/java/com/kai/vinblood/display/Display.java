package com.kai.vinblood.display;

import com.kai.vinblood.core.Input;
import com.kai.vinblood.core.Updatable;
import com.kai.vinblood.objs.GameObject;
import com.kai.vinblood.objs.ObjectController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Display extends JPanel implements Updatable {
    public static int DISPLAY_WIDTH = 1100, DISPLAY_HEIGHT = 700;
    private Input i;

    private static Display instance;
    private Display() {
        setPreferredSize(new Dimension(DISPLAY_WIDTH, DISPLAY_HEIGHT));

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                DISPLAY_WIDTH = getWidth();
                DISPLAY_HEIGHT = getHeight();

                for (GameObject o: ObjectController.getInstance().getGameObjects()) {
                    o.resizeImage();
                }
            }
        });

        setFocusable(true);
        i = new Input();
        addKeyListener(i);
        addMouseListener(i);

    }

    public static Display getInstance() {
        if (instance == null) instance = new Display();
        return instance;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int x = (int)getLocationOnScreen().getX();
        int y = (int)getLocationOnScreen().getY();
        i.mouseExists(x, y);

        for (GameObject o: ObjectController.getInstance().getGameObjects()) {
            if (o.isVisible()) {
                o.draw(g);
            }
        }
    }

    @Override
    public void update() {
        repaint();
        i.update();

    }
}
