package com.sw.view.components.automata.estados;

import com.sw.view.components.VNode;
import static com.sw.view.components.VNode.RADIO_NODO;
import com.sw.view.graphics.ColorPalette;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

/**
 *
 * @author HikingCarrot7
 */
public class EstadoFinal extends VNode {

    public static final int INTERN_CIRCLE_STROKE_WIDTH = 3;
    public static final int INTERN_CIRCLE_OFFSET = 6;

    public EstadoFinal(String name, int xCenter, int yCenter) {
        super(name, xCenter, yCenter);
    }

    @Override public void draw(Graphics2D g) {
        super.draw(g);

        Stroke defaultStroke = g.getStroke();
        Color defaultColor = g.getColor();

        g.setColor(colorPalette.getSpecificColor(ColorPalette.ColorKey.STROKE_COLOR_KEY));
        g.setStroke(new BasicStroke(INTERN_CIRCLE_STROKE_WIDTH));
        g.drawOval(
                xCenter - RADIO_NODO + INTERN_CIRCLE_OFFSET,
                yCenter - RADIO_NODO + INTERN_CIRCLE_OFFSET,
                (RADIO_NODO - INTERN_CIRCLE_OFFSET) * 2,
                (RADIO_NODO - INTERN_CIRCLE_OFFSET) * 2);

        g.setStroke(defaultStroke);
        g.setColor(defaultColor);
    }

}
