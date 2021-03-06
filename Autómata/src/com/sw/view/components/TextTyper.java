package com.sw.view.components;

import com.sw.controller.TextFormatter;
import com.sw.view.graphics.Drawable;
import com.sw.view.graphics.GraphicsUtils;
import com.sw.view.graphics.Movable;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;

/**
 *
 * @author HikingCarrot7
 */
public class TextTyper implements Drawable, Movable {

    private int xCenter;
    private int yCenter;
    private int maxCaracteres;
    private String text;
    private TextFormatter textFormatter;

    public static final TextFormatter DEFAULT_FORMATTER = keyCode -> keyCode > 33;
    public static final TextFormatter POSITIVE_INTEGER_FORMATTER = keyCode -> keyCode >= 48 && keyCode <= 57;

    public TextTyper(int xCenter, int yCenter, String text, int maxCaracteres) {
        this(xCenter, yCenter, text, maxCaracteres, DEFAULT_FORMATTER);
    }

    public TextTyper(Point coords, String text, int maxCaracteres, TextFormatter textFormatter) {
        this(coords.x, coords.y, text, maxCaracteres, textFormatter);
    }

    public TextTyper(int xCenter, int yCenter, String text, int maxCaracteres, TextFormatter textFormatter) {
        this.xCenter = xCenter;
        this.yCenter = yCenter;
        this.text = text;
        this.maxCaracteres = maxCaracteres;
        this.textFormatter = textFormatter;
    }

    public TextTyper(Point coords, int maxCaracteres, TextFormatter textFormatter) {
        this(coords.x, coords.y, "", maxCaracteres, textFormatter);
    }

    public TextTyper(Point coords, int maxCaracteres) {
        this(coords.x, coords.y, maxCaracteres);
    }

    public TextTyper(int xCenter, int yCenter, int maxCaracteres) {
        this(xCenter, yCenter, "", maxCaracteres);
    }

    @Override public void draw(Graphics2D g) {
        GraphicsUtils.dibujarStringEnPunto(g, text, xCenter, yCenter);
    }

    public void handleInputEvent(KeyEvent e) {
        if (text.length() >= 0 && e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
            text = quitarUltimoCharacter(text);

        else if (textFormatter.isValidCharacter(e.getKeyCode()) && text.length() < maxCaracteres)
            text += e.getKeyChar();
    }

    private String quitarUltimoCharacter(String cadena) {
        return cadena.length() <= 1 ? "" : cadena.substring(0, cadena.length() - 1);
    }

    @Override public int getXCenter() {
        return xCenter;
    }

    @Override public void setXCenter(int xPos) {
        this.xCenter = xPos;
    }

    @Override public int getYCenter() {
        return yCenter;
    }

    @Override public void setYCenter(int yPos) {
        this.yCenter = yPos;
    }

    public String getText() {
        return text;
    }

    public int getMaxCaracteres() {
        return maxCaracteres;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setMaxCaracteres(int maxCaracteres) {
        this.maxCaracteres = maxCaracteres;
    }

    @Override public Point getCoords() {
        return new Point(xCenter, yCenter);
    }
}
