package com.kai.vinblood.util;

import com.kai.vinblood.display.Display;

import java.awt.*;

public class VFont extends Font {

    public VFont(String name, int style, double sizeMultiplier) {
        super(name, style, (int)(Globals.ORIGINAL_FONT.getSize()*(Display.DISPLAY_WIDTH/(1100.0/sizeMultiplier))));
    }

    public VFont(double sizeMultiplier) {
        this(Globals.ORIGINAL_FONT.getName(), Globals.ORIGINAL_FONT.getStyle(), sizeMultiplier);
    }

}
