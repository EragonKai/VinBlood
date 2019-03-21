package com.kai.vinblood.display;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kai on Mar 20, 2019
 */
public class HUDController {
    private static HUDController instance;
    public static HUDController getInstance() {
        if (instance == null) instance = new HUDController();
        return instance;
    }

    List<Hoverable> hoverableList;
    List<Clickable> clickableList;

    private HUDController() {
        hoverableList = new ArrayList<>();
        clickableList = new ArrayList<>();
    }

    public void addHoverable(Hoverable h) {
        hoverableList.add(h);
    }

    public void addClickable(Clickable c) {
        clickableList.add(c);
    }

    public void doHovers(int mouseX, int mouseY) {
        hoverableList.forEach((h) -> {
            if (h.checkCollision(mouseX, mouseY)) {
                h.onHover();
            } else {
                h.onNotHovered();
            }
        });
    }

    public void doClicks(int mouseX, int mouseY) {
        clickableList.forEach((c) -> {
            if (c.checkCollision(mouseX, mouseY)) {
                c.onClick();
            }
        });
    }
}
