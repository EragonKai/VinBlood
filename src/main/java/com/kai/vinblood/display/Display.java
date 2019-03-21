package com.kai.vinblood.display;

import com.kai.vinblood.core.Input;
import com.kai.vinblood.core.Updatable;
import com.kai.vinblood.objs.GameObject;
import com.kai.vinblood.objs.ObjectController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;
import java.util.*;

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

        updateLayers();
    }

    public static Display getInstance() {
        if (instance == null) instance = new Display();
        return instance;
    }

    private HashMap<Integer, List<GameObject>> layers;
    private SortedSet<Integer> sortedLayers;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int x = (int)getLocationOnScreen().getX();
        int y = (int)getLocationOnScreen().getY();
        i.mouseExists(x, y);

        //Very inefficient
        //TODO: Clean this up.

        for (Integer i: sortedLayers) {
            //TODO: Why is this throwing a null pointer exception?
            for (GameObject o: layers.get(i)) {
                if (o.isVisible()) {
                    o.draw(g);
                }
            }
        }
    }

    public void updateLayers() {
        layers = new HashMap<>();
        for (GameObject o: ObjectController.getInstance().getGameObjects()) {
            Integer layer = o.getDisplayLayer();
            if (!layers.containsKey(layer)) {
                List<GameObject> thisLayer = new ArrayList<>();
                thisLayer.add(o);
                layers.put(layer, thisLayer);
            } else {
                layers.get(layer).add(o);
            }
        }
        sortedLayers = new TreeSet<>(layers.keySet());
    }

    @Override
    public void update() {
        repaint();
        i.update();

    }
}
