package com.kai.vinblood.util;

import com.kai.vinblood.display.Display;

public class Bounds {
    public int x, y;
    public int width, height;

    public Bounds(int x, int y) {
        this(x, y, -1, -1);
    }

    public Bounds(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Bounds(Bounds bounds) {
        this.x = bounds.x;
        this.y = bounds.y;
        this.width = bounds.width;
        this.height = bounds.height;
    }

    public double distanceTo (int tX, int tY) {
        double a = Math.abs( (x - tX));
        double b = Math.abs( (y - tY));
        return(Math.sqrt((a*a) + (b*b)));
    }

    public int scaledWidth() {
        return scaledWidthNumber(width);
    }

    public int scaledHeight() {
        return scaledHeightNumber(height);
    }

    public int scaledX() {
        return scaledWidthNumber(x);
    }

    public int scaledY() {
        return scaledHeightNumber(y);
    }

    public static int scaledWidthNumber(int start) {
        return (int)((double)start/1100 * Display.DISPLAY_WIDTH);
    }

    public static int scaledHeightNumber(int start) {
        return (int)((double)start/700 * Display.DISPLAY_HEIGHT);
    }

    public static int unScaledWidthNumber(int scaled) {
        return (int)((double)scaled * 1100/Display.DISPLAY_WIDTH);
    }

    public static int unScaledHeightNumber(int scaled) {
        return (int)((double)scaled * 700 / Display.DISPLAY_HEIGHT);
    }

    @Override
    public String toString() {
        return "Bounds{" +
                "x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
