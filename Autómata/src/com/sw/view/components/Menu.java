package com.sw.view.components;

import com.sw.view.components.automata.VAFND;
import com.sw.view.graphics.Drawable;
import com.sw.view.graphics.GraphicsUtils;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import javax.swing.JTextField;

/**
 *
 * @author HikingCarrot7
 */
public class Menu implements Drawable {

    public static final Color GRAY_TEXT_COLOR = new Color(159, 162, 166);
    public static final int MENU_WIDTH = 290;

    public static final int ESTADO_INICIAL_ID = 1;
    public static final int ESTADO_NORMAL_ID = 2;
    public static final int ESTADO_FINAL_ID = 3;
    public static final int ESTADO_INICIAL_FINAL_ID = 4;
    public static final int MOVER_ESTADO_ID = 5;
    public static final int ELIMINAR_ESTADO_ID = 6;
    public static final int CONEXION_NORMAL_ID = 7;
    public static final int CONEXION_BUCLE_ID = 8;
    public static final int ELIMINAR_CONEXION_ID = 9;
    public static final int COMPROBAR_AUTOMATA_ID = 10;
    public static final int COMPROBACION_PASOS_ID = 11;

    public static AbstractButton BUTTON_ADD_ESTADO_INICIAL = new AbstractButton.ButtonBuilder()
            .addLine("Estado inicial")
            .setID(ESTADO_INICIAL_ID)
            .build();

    public static AbstractButton BUTTON_ADD_ESTADO_NORMAL = new AbstractButton.ButtonBuilder()
            .addLine("Estado normal")
            .setID(ESTADO_NORMAL_ID)
            .build();

    public static AbstractButton BUTTON_ADD_ESTADO_FINAL = new AbstractButton.ButtonBuilder()
            .addLine("Estado final")
            .setID(ESTADO_FINAL_ID)
            .build();

    public static AbstractButton BUTTON_ADD_ESTADO_INICIAL_FINAL = new AbstractButton.ButtonBuilder()
            .addLine("Estado inicial-")
            .addLine("final")
            .setID(ESTADO_INICIAL_FINAL_ID)
            .build();

    public static AbstractButton BUTTON_MOVER_ESTADO = new AbstractButton.ButtonBuilder()
            .addLine("Mover estado")
            .setID(MOVER_ESTADO_ID)
            .build();

    public static AbstractButton BUTTON_ELIMINAR_ESTADO = new AbstractButton.ButtonBuilder()
            .addLine("Borrar estado")
            .setID(ELIMINAR_ESTADO_ID)
            .build();

    public static AbstractButton BUTTON_ADD_CONEXION_NORMAL = new AbstractButton.ButtonBuilder()
            .addLine("Conexi??n")
            .addLine("normal")
            .setID(CONEXION_NORMAL_ID)
            .build();

    public static AbstractButton BUTTON_ADD_CONEXION_BUCLE = new AbstractButton.ButtonBuilder()
            .addLine("Conexi??n")
            .addLine("bucle")
            .setID(CONEXION_BUCLE_ID)
            .build();

    public static AbstractButton BUTTON_ELIMINAR_CONEXION = new AbstractButton.ButtonBuilder()
            .addLine("Borrar")
            .addLine("conexi??n")
            .setID(ELIMINAR_CONEXION_ID)
            .build();

    public static AbstractButton BUTTON_COMPROBAR_AUTOMATA = new AbstractButton.ButtonBuilder()
            .addLine("COMPROBAR")
            .setColorPalette(Button.BUTTON_COLOR_PALETTE)
            .setDimension(50, 40)
            .setFontSize(8)
            .setID(COMPROBAR_AUTOMATA_ID)
            .build();

    public static AbstractButton BUTTON_COMPROBACION_PASOS_AUTOMATA = new AbstractButton.ButtonBuilder()
            .addLine("Paso a paso")
            .setID(COMPROBACION_PASOS_ID)
            .build();

    public static JTextField TEXT_FIELD = new JTextField();

    private final VAFND vafnd;

    public Menu(VAFND vafnd) {
        this.vafnd = vafnd;
        initComponents();
    }

    private void initComponents() {
        vafnd.addSwingComponent(TEXT_FIELD);
    }

    @Override public void draw(Graphics2D g) {
        Stroke defaultStroke = g.getStroke();
        Color defaultColor = g.getColor();

        drawBackground(g);
        drawLabels(g);
        drawButtons(g);

        g.setStroke(defaultStroke);
        g.setColor(defaultColor);
    }

    private void drawButtons(Graphics2D g) {
        BUTTON_ADD_ESTADO_INICIAL.setPosition(vafnd.getWidth() - 260, 80);
        BUTTON_ADD_ESTADO_NORMAL.setPosition(vafnd.getWidth() - 140, 80);
        BUTTON_ADD_ESTADO_FINAL.setPosition(vafnd.getWidth() - 260, 140);
        BUTTON_ADD_ESTADO_INICIAL_FINAL.setPosition(vafnd.getWidth() - 140, 140);
        BUTTON_MOVER_ESTADO.setPosition(vafnd.getWidth() - 260, 200);
        BUTTON_ELIMINAR_ESTADO.setPosition(vafnd.getWidth() - 140, 200);
        BUTTON_ADD_CONEXION_NORMAL.setPosition(vafnd.getWidth() - 260, 290);
        BUTTON_ADD_CONEXION_BUCLE.setPosition(vafnd.getWidth() - 140, 290);
        BUTTON_ELIMINAR_CONEXION.setPosition(vafnd.getWidth() - 200, 350);
        BUTTON_COMPROBAR_AUTOMATA.setPosition(vafnd.getWidth() - 80, 440);
        BUTTON_COMPROBACION_PASOS_AUTOMATA.setPosition(vafnd.getWidth() - 200, 490);
        TEXT_FIELD.setBounds(vafnd.getWidth() - 200, 440, 110, 40);

        BUTTON_ADD_ESTADO_INICIAL.draw(g);
        BUTTON_ADD_ESTADO_NORMAL.draw(g);
        BUTTON_ADD_ESTADO_FINAL.draw(g);
        BUTTON_ADD_ESTADO_INICIAL_FINAL.draw(g);
        BUTTON_MOVER_ESTADO.draw(g);
        BUTTON_ELIMINAR_ESTADO.draw(g);
        BUTTON_ADD_CONEXION_NORMAL.draw(g);
        BUTTON_ADD_CONEXION_BUCLE.draw(g);
        BUTTON_ELIMINAR_CONEXION.draw(g);
        BUTTON_COMPROBAR_AUTOMATA.draw(g);
        BUTTON_COMPROBACION_PASOS_AUTOMATA.draw(g);
    }

    private void drawLabels(Graphics2D g) {
        g.setColor(Color.WHITE);
        GraphicsUtils.dibujarStringEnPunto(g, "Men??", vafnd.getWidth() - 285 / 2, 20);

        g.setColor(GRAY_TEXT_COLOR);
        GraphicsUtils.dibujarStringEnPunto(g, "Agregar estados", vafnd.getWidth() - 285 / 2, 55);
        GraphicsUtils.dibujarStringEnPunto(g, "Agregar conexiones", vafnd.getWidth() - 285 / 2, 267);
        GraphicsUtils.dibujarStringEnPunto(g, "Ejecutar aut??mata", vafnd.getWidth() - 285 / 2, 415);
    }

    private void drawBackground(Graphics2D g) {
        g.setColor(ToggleButton.DEFAULT_COLOR);
        g.fillRect(vafnd.getWidth() - MENU_WIDTH, 0, MENU_WIDTH, vafnd.getHeight());

        g.setColor(Color.WHITE);
        g.fillRect(vafnd.getWidth() - 285, 40, 285, vafnd.getHeight());
    }

    public boolean clicked(Point point) {
        return GraphicsUtils.intersects(
                new Rectangle(vafnd.getWidth() - MENU_WIDTH, 0, MENU_WIDTH, vafnd.getHeight()),
                new Rectangle(point));
    }

}
