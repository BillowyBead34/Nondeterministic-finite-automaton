package com.sw.view.components;

import com.sw.view.components.automata.VAFND;
import com.sw.view.graphics.Box;
import com.sw.view.graphics.ColorPalette;
import com.sw.view.graphics.DialogueBalloonFactory;
import com.sw.view.graphics.Drawable;
import com.sw.view.graphics.GraphicsUtils;
import com.sw.view.graphics.Movable;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;

/**
 *
 * @author HikingCarrot7
 */
public class DialogueBalloon implements Box, Drawable {

    public static final int RADIO_BORDER = 10;
    public static final int PADDING = 7;
    public static final int MARGIN = 5;
    public static final int TRIANGLE_HEIGHT = 10;
    public static final int TRIANGLE_LENGTH = 25;

    public static final Color DEFAULT_GLOBE_FILL_COLOR = new Color(249, 247, 136);
    public static final Color DEFAULT_GLOBLE_STROKE_COLOR = new Color(241, 221, 1);

    public static final ColorPalette DEFAULT_GLOBE_COLOR_PALETTE = new ColorPalette.ColorPaletteBuilder()
            .addColor(ColorPalette.ColorKey.FILL_COLOR_KEY, DEFAULT_GLOBE_FILL_COLOR)
            .addColor(ColorPalette.ColorKey.STROKE_COLOR_KEY, DEFAULT_GLOBLE_STROKE_COLOR)
            .addColor(ColorPalette.ColorKey.TEXT_COLOR_KEY, Color.BLACK)
            .build();

    private int xPos;
    private int yPos;
    private int width;
    private int height;
    private String text;
    private VAFND vgraph;
    private Blob relativeTo;
    private ColorPalette colorPalette;
    private BoxPosition boxPosition;

    public DialogueBalloon(int xPos, int yPos, int width, int height, String text, ColorPalette colorPalette, BoxPosition boxPosition) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.text = text;
        this.colorPalette = colorPalette;
        this.boxPosition = boxPosition;
    }

    public DialogueBalloon(int xPos, int yPos, int width, int height, String text) {
        this(xPos, yPos, width, height, text, DEFAULT_GLOBE_COLOR_PALETTE, BoxPosition.TOP);
    }

    public DialogueBalloon(VAFND vgraph, Blob relativeTo, String text, ColorPalette colorPalette) {
        this.relativeTo = relativeTo;
        this.text = text;
        this.colorPalette = colorPalette;
        this.vgraph = vgraph;
    }

    public DialogueBalloon(VAFND vgraph, Blob relativeTo, String text) {
        this(vgraph, relativeTo, text, DEFAULT_GLOBE_COLOR_PALETTE);
    }

    @Override public void draw(Graphics2D g) {
        Stroke defaultStroke = g.getStroke();
        Color defaultColor = g.getColor();

        if (relativeTo != null)
            drawRelativeTo(g, vgraph, relativeTo, text);
        else
            drawBox(g, xPos, yPos, width, height, boxPosition);

        g.setStroke(defaultStroke);
        g.setColor(defaultColor);
    }

    private void drawRelativeTo(Graphics2D g, VAFND vgraph, Blob relativeTo, String text) {
        Rectangle bounds = GraphicsUtils.getStringBounds(g, text);
        int xPos = 0;
        int yPos = 0;
        BoxPosition boxPosition = null;

        if (canPlaceOnTop(vgraph, relativeTo, bounds)) {
            xPos = relativeTo.getXCenter() - bounds.width / 2 - 7;
            yPos = relativeTo.getYCenter() - relativeTo.getRadio() - (bounds.height + PADDING * 2) - TRIANGLE_HEIGHT - MARGIN;
            boxPosition = BoxPosition.TOP;

        } else if (canPlaceOnRight(vgraph, relativeTo, bounds)) {
            xPos = relativeTo.getXCenter() + relativeTo.getRadio() + MARGIN + TRIANGLE_HEIGHT;
            yPos = relativeTo.getYCenter() - (bounds.height / 2 + PADDING);
            boxPosition = BoxPosition.RIGHT;

        } else if (canPlaceOnBottom(vgraph, relativeTo, bounds)) {
            xPos = relativeTo.getXCenter() - bounds.width / 2 - 7;
            yPos = relativeTo.getYCenter() + relativeTo.getRadio() + TRIANGLE_HEIGHT + MARGIN;
            boxPosition = BoxPosition.BOTTOM;

        } else if (canPlaceOnLeft(vgraph, relativeTo, bounds)) {
            xPos = relativeTo.getXCenter() - (bounds.width + PADDING * 2) - TRIANGLE_HEIGHT - MARGIN - relativeTo.getRadio();
            yPos = relativeTo.getYCenter() - (bounds.height / 2 + PADDING);
            boxPosition = BoxPosition.LEFT;
        }

        drawBox(g, xPos, yPos, bounds.width + PADDING * 2, bounds.height + PADDING * 2, boxPosition);
    }

    private boolean canPlaceOnTop(VAFND vgraph, Movable relativeTo, Rectangle textBounds) {
        int realHeight = getRealBoxHeight(textBounds);
        int realWidth = getRealBoxWidth(textBounds);

        return relativeTo.getYCenter() - VNode.RADIO_NODO >= realHeight
                && relativeTo.getXCenter() + realWidth / 2 <= vgraph.getWidth() - Menu.MENU_WIDTH
                && relativeTo.getXCenter() - realWidth / 2 >= 0;
    }

    private boolean canPlaceOnBottom(VAFND vgraph, Movable relativeTo, Rectangle textBounds) {
        int realHeight = getRealBoxHeight(textBounds);
        int realWidth = getRealBoxWidth(textBounds);

        return relativeTo.getYCenter() + VNode.RADIO_NODO + realHeight <= vgraph.getHeight()
                && relativeTo.getXCenter() + realWidth / 2 <= vgraph.getWidth() - Menu.MENU_WIDTH;
    }

    private boolean canPlaceOnRight(VAFND vgraph, Movable relativeTo, Rectangle textBounds) {
        int realWidth = getRealBoxWidth(textBounds);

        return relativeTo.getXCenter() + VNode.RADIO_NODO + realWidth <= vgraph.getWidth() - Menu.MENU_WIDTH;
    }

    private boolean canPlaceOnLeft(VAFND vgraph, Movable relativeTo, Rectangle textBounds) {
        int realWidth = getRealBoxWidth(textBounds);

        return relativeTo.getXCenter() - VNode.RADIO_NODO - realWidth >= 0;
    }

    @Override public void drawBox(Graphics2D g, int xPos, int yPos, int width, int height, BoxPosition boxPosition) {
        Shape shape = DialogueBalloonFactory.getInstance().createShape(xPos, yPos, width, height, boxPosition);

        g.setColor(colorPalette.getSpecificColor(ColorPalette.ColorKey.FILL_COLOR_KEY));
        g.fill(shape);

        g.setColor(colorPalette.getSpecificColor(ColorPalette.ColorKey.STROKE_COLOR_KEY));
        g.setStroke(new BasicStroke(2));
        g.draw(shape);

        g.setColor(colorPalette.getSpecificColor(ColorPalette.ColorKey.TEXT_COLOR_KEY));
        GraphicsUtils.dibujarStringEnPunto(g, text, xPos + width / 2, yPos + height / 2);
    }

    private int getRealBoxHeight(Rectangle textBounds) {
        return textBounds.height + PADDING * 2 + TRIANGLE_HEIGHT + MARGIN;
    }

    private int getRealBoxWidth(Rectangle textBounds) {
        return textBounds.width + PADDING * 2 + TRIANGLE_HEIGHT + MARGIN;
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override public int getWidth() {
        return width;
    }

    @Override public int getHeight() {
        return height;
    }

}
