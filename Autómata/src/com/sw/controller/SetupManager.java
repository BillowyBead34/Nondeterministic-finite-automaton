package com.sw.controller;

import com.sw.controller.events.EventHandler;
import com.sw.model.automata.AFND;
import com.sw.model.states.AddAFNDConexionBucleState;
import com.sw.model.states.AddAFNDConexionNormalState;
import com.sw.model.states.AddAFNDEstadoState;
import com.sw.model.states.BorrarAFNDConexionState;
import com.sw.model.states.BorrarAFNDEstadoState;
import com.sw.model.states.ComprobarAFNDState;
import com.sw.model.states.MoverAFNDEstadoState;
import com.sw.model.states.PasoAPasoAFNDState;
import com.sw.view.MainView;
import com.sw.view.components.Menu;
import com.sw.view.components.automata.VAFND;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author HikingCarrot7
 */
public class SetupManager {

    private final MainView view;

    public SetupManager(MainView view) {
        this.view = view;
        init();
    }

    private void init() {
        try {
            GraphicsEnvironment
                    .getLocalGraphicsEnvironment()
                    .registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("res/fonts/OpenSans-SemiBold.ttf")));
        } catch (FontFormatException | IOException e) {
            System.out.println(e.getMessage());
        }

        VAFND vafnd = VAFND.getInstance();
        AFND afnd = new AFND();
        Menu menu = new Menu(vafnd);

        AFNDController afndController = new AFNDController(view, afnd, vafnd, menu);
        EventHandler eventHandler = EventHandler.getInstance();

        vafnd.addKeyListener(eventHandler);
        vafnd.addMouseListener(eventHandler);
        vafnd.addMouseMotionListener(eventHandler);

        afndController.addBinding(Menu.BUTTON_ADD_ESTADO_INICIAL, AddAFNDEstadoState.getInstance());
        afndController.addBinding(Menu.BUTTON_ADD_ESTADO_NORMAL, AddAFNDEstadoState.getInstance());
        afndController.addBinding(Menu.BUTTON_ADD_ESTADO_INICIAL_FINAL, AddAFNDEstadoState.getInstance());
        afndController.addBinding(Menu.BUTTON_ADD_ESTADO_FINAL, AddAFNDEstadoState.getInstance());

        afndController.addBinding(Menu.BUTTON_MOVER_ESTADO, MoverAFNDEstadoState.getInstance());
        afndController.addBinding(Menu.BUTTON_ADD_CONEXION_NORMAL, AddAFNDConexionNormalState.getInstance());
        afndController.addBinding(Menu.BUTTON_ELIMINAR_ESTADO, BorrarAFNDEstadoState.getInstance());
        afndController.addBinding(Menu.BUTTON_ADD_CONEXION_BUCLE, AddAFNDConexionBucleState.getInstance());
        afndController.addBinding(Menu.BUTTON_ELIMINAR_CONEXION, BorrarAFNDConexionState.getInstance());
        afndController.addBinding(Menu.BUTTON_COMPROBAR_AUTOMATA, ComprobarAFNDState.getInstance());
        afndController.addBinding(Menu.BUTTON_COMPROBACION_PASOS_AUTOMATA, PasoAPasoAFNDState.getInstance());

        eventHandler.addObserver(afndController);

        vafnd.addComponent(menu, VAFND.MAX_LAYER);

        view.add(vafnd, BorderLayout.CENTER);
        view.validate();
    }

}
