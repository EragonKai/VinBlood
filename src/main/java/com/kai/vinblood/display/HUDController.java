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
    List<Hoverable> hoverableToAdd;
    List<Clickable> clickableList;
    List<Clickable> clickableToAdd;

    private HUDController() {
        hoverableList = new ArrayList<>();
        clickableList = new ArrayList<>();
        hoverableToAdd = new ArrayList<>();
        clickableToAdd = new ArrayList<>();
    }

    public void addHoverable(Hoverable h) {
        hoverableToAdd.add(h);
    }

    public void addClickable(Clickable c) {
        clickableToAdd.add(c);
    }

    public void doHovers(int mouseX, int mouseY) {
        hoverableList.forEach((h) -> {
            if (h.checkCollision(mouseX, mouseY)) {
                h.onHover();
            } else {
                h.onNotHovered();
            }
        });

        if (hoverableToAdd.size() > 0) {
            hoverableList.addAll(hoverableToAdd);
            hoverableToAdd.clear();
        }
    }

    public void doClicks(int mouseX, int mouseY) {
        clickableList.forEach((c) -> {
            if (c.checkCollision(mouseX, mouseY)) {
                c.onClick();
            }
        });

        if (clickableToAdd.size() > 0) {
            clickableList.addAll(clickableToAdd);
            clickableToAdd.clear();
        }
    }

    public List<Hoverable> getHoverableList() {
        return hoverableList;
    }

    public List<Clickable> getClickableList() {
        return clickableList;
    }
}
