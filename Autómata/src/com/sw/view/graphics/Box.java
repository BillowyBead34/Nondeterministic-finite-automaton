package com.sw.view.graphics;

import java.awt.Graphics2D;

/**
 *
 * @author HikingCarrot7
 */
public interface Box {

    public static enum BoxPosition {
        TOP,
        BOTTOM,
        LEFT,
        RIGHT,
        TOP_RIGHT,
        TOP_LEFT,
        BOTTOM_RIGHT,
        BOTTOM_LEFT
    }

    public void drawBox(Graphics2D g, int xPos, int yPos, int width, int height, BoxPosition boxPosition);

    public int getWidth();

    public int getHeight();

}
