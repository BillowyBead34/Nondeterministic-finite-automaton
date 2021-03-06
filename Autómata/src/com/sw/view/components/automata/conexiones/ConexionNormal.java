package com.sw.view.components.automata.conexiones;

import com.sw.model.utils.MathHelper;
import com.sw.view.components.Menu;
import com.sw.view.components.VArch;
import static com.sw.view.components.VArch.ALTURA_CURVATURA;
import static com.sw.view.components.VArch.BLOB_PADDING;
import static com.sw.view.components.VArch.STROKE_WIDTH;
import static com.sw.view.components.VArch.TRIANGLE_LENGTH;
import com.sw.view.components.VNode;
import com.sw.view.graphics.ColorPalette;
import com.sw.view.graphics.GraphicsUtils;
import com.sw.view.graphics.Movable;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.GeneralPath;

/**
 *
 * @author HikingCarrot7
 */
public class ConexionNormal extends VArch {

    public ConexionNormal(Movable origen, Movable destino, String condicion, ColorPalette colorPalette) {
        super(origen, destino, condicion, colorPalette);
    }

    public ConexionNormal(Movable origen, Movable destino, String condicion, boolean previewMode) {
        super(origen, destino, condicion, previewMode);
    }

    public ConexionNormal(Movable origen, Movable condicion, String character) {
        super(origen, condicion, character);
    }

    public ConexionNormal(Movable origen, Movable destino, boolean previewMode) {
        super(origen, destino, previewMode);
    }

    @Override public void draw(Graphics2D g) {
        Stroke defaultStroke = g.getStroke();
        Color defaultColor = g.getColor();

        g.setColor(colorPalette.getSpecificColor(ColorPalette.ColorKey.FILL_COLOR_KEY));
        g.setStroke(new BasicStroke(STROKE_WIDTH));

        if (previewMode)
            g.drawLine(origen.getXCenter(), origen.getYCenter(), destino.getXCenter(), destino.getYCenter());
        else {
            GeneralPath path = new GeneralPath();
            path.moveTo(origen.getXCenter(), origen.getYCenter());

            Point puntoMedio = MathHelper.puntoMedio(origen.getCoords(), destino.getCoords());
            Point puntoControl = MathHelper.puntoControl(origen.getCoords(), destino.getCoords(), ALTURA_CURVATURA);

            path.curveTo(
                    puntoMedio.x + puntoControl.x,
                    puntoMedio.y + puntoControl.y,
                    puntoMedio.x + puntoControl.x,
                    puntoMedio.y + puntoControl.y,
                    destino.getXCenter(),
                    destino.getYCenter());

            g.draw(path);

            updateBlobPosition(g, origen.getCoords(), destino.getCoords());
            updateTrianglePosition(g, origen.getCoords(), destino.getCoords(), ALTURA_CURVATURA);
        }

        g.setStroke(defaultStroke);
        g.setColor(defaultColor);
    }

    @Override public void updateTrianglePosition(Graphics2D g, Point origen, Point destino, int alturaCurvatura) {
        Point puntoMedio = MathHelper.puntoMedio(origen, destino);
        Point puntoControl = MathHelper.puntoControl(origen, destino, alturaCurvatura);

        triangle.setOrigenX(puntoMedio.x + puntoControl.x);
        triangle.setOrigenY(puntoMedio.y + puntoControl.y);
        triangle.setDestinoX(destino.x);
        triangle.setDestinoY(destino.y);
        triangle.setLength(TRIANGLE_LENGTH);
        triangle.setOffset(VNode.RADIO_NODO + STROKE_WIDTH);
    }

    @Override public void updateBlobPosition(Graphics2D g, Point origen, Point destino) {
        Point puntoMedio = MathHelper.puntoMedio(origen, destino);
        Point puntoControl = MathHelper.puntoControl(origen, destino, ALTURA_CURVATURA);
        g.setColor(Menu.GRAY_TEXT_COLOR);
        int textWidth = GraphicsUtils.getStringWidth(g, condicion);
        int blobRadio = textWidth / 2 + BLOB_PADDING * 2;

        blob.setName(condicion);
        blob.setRadio(blobRadio);
        blob.setXCenter(puntoMedio.x + puntoControl.x);
        blob.setYCenter(puntoMedio.y + puntoControl.y);
    }

}
