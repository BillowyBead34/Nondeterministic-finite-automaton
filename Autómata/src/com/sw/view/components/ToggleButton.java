package com.sw.view.components;

import com.sw.view.graphics.ColorPalette;
import com.sw.view.graphics.Togglable;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.InputEvent;
import java.util.List;

/**
 *
 * @author HikingCarrot7
 */
public class ToggleButton extends AbstractButton implements Togglable {

    public static final int ARC_WIDTH = 25;
    public static final int ARC_HEIGHT = 25;
    public static final int STROKE_WIDTH = 2;

    public static final ColorPalette TOGGLED_BUTTON_COLOR_PALETTE = new ColorPalette.ColorPaletteBuilder()
            .addColor(ColorPalette.ColorKey.FILL_COLOR_KEY, DEFAULT_COLOR)
            .addColor(ColorPalette.ColorKey.STROKE_COLOR_KEY, DEFAULT_COLOR)
            .addColor(ColorPalette.ColorKey.TEXT_COLOR_KEY, Color.WHITE)
            .build();

    private boolean toggled = false;

    public ToggleButton(List<String> content, int xPos, int yPos, int width, int height, int id, int fontSize, ColorPalette colorPalette) {
        super(content, xPos, yPos, width, height, id, fontSize, colorPalette);
    }

    public ToggleButton(List<String> content, Point coords, Dimension dimension, int id, int fontSize, ColorPalette colorPalette) {
        super(content, coords, dimension, id, fontSize, colorPalette);
    }

    @Override public void draw(Graphics2D g) {
        Stroke defaultStroke = g.getStroke();
        Color defaultColor = g.getColor();
        Font defaultFont = g.getFont();

        g.setFont(new Font(defaultFont.getName(), defaultFont.getStyle(), fontSize));
        g.setColor(colorPalette.getSpecificColor(ColorPalette.ColorKey.FILL_COLOR_KEY));
        g.fillRoundRect(xPos, yPos, width, height, ARC_WIDTH, ARC_HEIGHT);

        g.setColor(colorPalette.getSpecificColor(ColorPalette.ColorKey.STROKE_COLOR_KEY));
        g.setStroke(new BasicStroke(STROKE_WIDTH));
        g.drawRoundRect(xPos, yPos, width, height, ARC_WIDTH, ARC_HEIGHT);

        g.setColor(colorPalette.getSpecificColor(ColorPalette.ColorKey.TEXT_COLOR_KEY));
        super.drawContent(g);

        g.setFont(defaultFont);
        g.setStroke(defaultStroke);
        g.setColor(defaultColor);
    }

    @Override public void click(InputEvent event) {
        toggle();
    }

    @Override public void blur() {
        toggled = false;
        colorPalette = DEFAULT_BUTTON_COLOR_PALETTE;
    }

    @Override public void toggle() {
        toggled = !toggled;
        colorPalette = toggled ? TOGGLED_BUTTON_COLOR_PALETTE : DEFAULT_BUTTON_COLOR_PALETTE;
    }

    public boolean isToggled() {
        return toggled;
    }

    public void setToggled(boolean toggled) {
        this.toggled = toggled;
    }

}
