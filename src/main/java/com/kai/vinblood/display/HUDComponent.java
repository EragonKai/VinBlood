package com.kai.vinblood.display;

import com.kai.vinblood.core.ResourceManager;
import com.kai.vinblood.util.Bounds;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public abstract class HUDComponent extends JComponent implements MouseListener {
    private BufferedImage image;

    public HUDComponent(Bounds bounds, BufferedImage givenImage) {
        setBounds(bounds.scaledX(), bounds.scaledY(), bounds.scaledWidth(), bounds.scaledHeight());
        this.image = givenImage;

        addMouseListener(this);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                image = ResourceManager.toBufferedImage(image.getScaledInstance(bounds.scaledWidth(), bounds.scaledHeight(), Image.SCALE_FAST));
                setBounds(bounds.scaledX(), bounds.scaledY(), bounds.scaledWidth(), bounds.scaledHeight());
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, getX(), getY(), null);
    }
}
