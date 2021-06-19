package com.sw.view.graphics;

import java.awt.Shape;

/**
 *
 * @author HikingCarrot7
 */
public interface ShapeFactory {

    public static final String DIALOGUE_BALLOON_SHAPE = "DIALOGUE_BALLOON";
    public static final String TEXTBOX_SHAPE = "TEXTBOX";

    public Shape createShape(int xPos, int yPos, int width, int height, Box.BoxPosition boxPosition);

}
