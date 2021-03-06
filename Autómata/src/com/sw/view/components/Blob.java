package com.sw.view.components;

import com.sw.view.graphics.ColorPalette;
import com.sw.view.graphics.Drawable;
import com.sw.view.graphics.GraphicsUtils;
import com.sw.view.graphics.Movable;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 *
 * @author HikingCarrot7
 */
public class Blob implements Drawable, Movable {

    protected int xCenter;
    protected int yCenter;
    protected int radio;
    protected String name;
    protected ColorPalette colorPalette;

    public static final ColorPalette DEFAULT_BLOB_COLOR_PALETTE = new ColorPalette.ColorPaletteBuilder()
            .addColor(ColorPalette.ColorKey.FILL_COLOR_KEY, Menu.GRAY_TEXT_COLOR)
            .addColor(ColorPalette.ColorKey.TEXT_COLOR_KEY, Menu.GRAY_TEXT_COLOR)
            .build();

    public static final ColorPalette SELECTED_BLOB_COLOR_PALETTE = new ColorPalette.ColorPaletteBuilder()
            .addColor(ColorPalette.ColorKey.FILL_COLOR_KEY, VArch.COLOR_SELECTED_ARCH)
            .addColor(ColorPalette.ColorKey.TEXT_COLOR_KEY, Color.WHITE)
            .build();

    public static final ColorPalette RED_BLOB_COLOR_PALETTE = new ColorPalette.ColorPaletteBuilder()
            .addColor(ColorPalette.ColorKey.FILL_COLOR_KEY, new Color(255, 80, 80))
            .addColor(ColorPalette.ColorKey.TEXT_COLOR_KEY, Menu.GRAY_TEXT_COLOR)
            .build();

    public Blob() {
        this(0, 0, 0, "", DEFAULT_BLOB_COLOR_PALETTE);
    }

    public Blob(int xCenter, int yCenter, int radio, String name, ColorPalette colorPalette) {
        this.xCenter = xCenter;
        this.yCenter = yCenter;
        this.radio = radio;
        this.name = name;
        this.colorPalette = colorPalette;
    }

    @Override public void draw(Graphics2D g) {
        Color defaultColor = g.getColor();

        g.setColor(colorPalette.getSpecificColor(ColorPalette.ColorKey.TEXT_COLOR_KEY));
        GraphicsUtils.dibujarStringEnPunto(g, name, xCenter, yCenter);

        g.setColor(defaultColor);
    }

    public int getRadio() {
        return radio;
    }

    public void setRadio(int radio) {
        this.radio = radio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ColorPalette getColorPalette() {
        return colorPalette;
    }

    public void setColorPalette(ColorPalette colorPalette) {
        this.colorPalette = colorPalette;
    }

    @Override public int getXCenter() {
        return xCenter;
    }

    @Override public void setXCenter(int xCenter) {
        this.xCenter = xCenter;
    }

    @Override public int getYCenter() {
        return yCenter;
    }

    @Override public void setYCenter(int yCenter) {
        this.yCenter = yCenter;
    }

    @Override public Point getCoords() {
        return new Point(xCenter, yCenter);
    }

}
