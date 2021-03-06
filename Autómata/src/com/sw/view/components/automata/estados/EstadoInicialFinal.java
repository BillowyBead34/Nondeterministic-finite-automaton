package com.sw.view.components.automata.estados;

import com.sw.view.graphics.ColorPalette;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

/**
 *
 * @author HikingCarrot7
 */
public class EstadoInicialFinal extends EstadoInicial {

    public EstadoInicialFinal(String name, int xCenter, int yCenter) {
        super(name, xCenter, yCenter);
    }

    @Override public void draw(Graphics2D g) {
        super.draw(g);

        Stroke defaultStroke = g.getStroke();
        Color defaultColor = g.getColor();

        g.setColor(colorPalette.getSpecificColor(ColorPalette.ColorKey.STROKE_COLOR_KEY));
        g.setStroke(new BasicStroke(EstadoFinal.INTERN_CIRCLE_STROKE_WIDTH));
        g.drawOval(
                xCenter - RADIO_NODO + EstadoFinal.INTERN_CIRCLE_OFFSET,
                yCenter - RADIO_NODO + EstadoFinal.INTERN_CIRCLE_OFFSET,
                (RADIO_NODO - EstadoFinal.INTERN_CIRCLE_OFFSET) * 2,
                (RADIO_NODO - EstadoFinal.INTERN_CIRCLE_OFFSET) * 2);

        g.setStroke(defaultStroke);
        g.setColor(defaultColor);
    }

}
