package com.sw.model.states;

import com.sw.model.automata.AFND;
import com.sw.model.automata.exceptions.CadenaVaciaException;
import com.sw.model.automata.exceptions.NoExisteEstadoInicialException;
import com.sw.model.graph.Arch;
import com.sw.model.utils.Pair;
import com.sw.view.components.Menu;
import com.sw.view.components.TextBox;
import com.sw.view.components.Triangle;
import com.sw.view.components.VArch;
import com.sw.view.components.VNode;
import com.sw.view.components.automata.VAFND;
import com.sw.view.graphics.Box;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.util.Iterator;

/**
 *
 * @author HikingCarrot7
 */
public class PasoAPasoAFNDState implements AFNDState {

    private static PasoAPasoAFNDState instance;

    public synchronized static PasoAPasoAFNDState getInstance() {
        if (instance == null)
            instance = new PasoAPasoAFNDState();

        return instance;
    }

    private boolean comprobado = false;
    private boolean palabraAceptada = false;
    private Iterator<Pair<Arch<?>, String>> recorridoIterator;
    private final TextBox messageBox;

    private PasoAPasoAFNDState() {
        messageBox = new TextBox.TextBoxBuilder()
                .setBoxPosition(Box.BoxPosition.TOP_LEFT)
                .build();
    }

    @Override public void updateGraphState(AFND<String> afnd, VAFND vafnd, AFNDStateManager afndStateManager, InputEvent event, int buttonID) {
        if (event.getID() == MouseEvent.MOUSE_CLICKED)
            if (palabraAceptada)
                pintarSiguientePaso(afnd, vafnd);
            else
                comprobarAutomata(afnd, vafnd);

        if (!comprobado)
            comprobarAutomata(afnd, vafnd);

        vafnd.repaint();
    }

    private boolean comprobarAutomata(AFND<String> afnd, VAFND vafnd) {
        String text = Menu.TEXT_FIELD.getText();
        try {
            boolean matches = afnd.matches(text);
            vafnd.addComponent(messageBox, VAFND.MAX_LAYER);

            if (matches) {
                messageBox.setTitle("Palabra ACEPTADA, presiona sobre el a??tomata para iniciar el recorrido");
                messageBox.setColorPalette(TextBox.GREEN_TEXTBOX_COLOR_PALETTE);
                palabraAceptada = true;
            } else {
                messageBox.setTitle("Palabra NO ACEPTADA, no podemos iniciar el recorrido");
                messageBox.setColorPalette(TextBox.RED_TEXTBOX_COLOR_PALETTE);
                palabraAceptada = false;
            }

            comprobado = true;
            return matches;

        } catch (CadenaVaciaException | NoExisteEstadoInicialException e) {
            vafnd.getDefaultTextBox().setTitle(e.getMessage());
            comprobado = true;
            return false;
        }
    }

    private void pintarSiguientePaso(AFND<String> afnd, VAFND vafnd) {
        if (recorridoIterator == null)
            recorridoIterator = afnd.getRecorrido().iterator();

        if (recorridoIterator.hasNext()) {
            Pair<Arch<?>, String> pair = recorridoIterator.next();
            Arch<?> arch = pair.getLeft();
            clearAllMarks(vafnd);

            VNode origen = vafnd.getVNode(arch.getOrigen().getName().toString());
            VNode destino = vafnd.getVNode(arch.getDestino().getName().toString());
            VArch varch = vafnd.getVArch(origen, destino);

            origen.setColorPalette(VNode.SELECTED_RUTA_VNODE_COLOR_PALETTE);
            destino.setColorPalette(VNode.SELECTED_RUTA_VNODE_COLOR_PALETTE);
            varch.setColorPalette(VArch.SELECTED_VARCH_COLOR_PALETTE);
            varch.getTriangle().setColorPalette(VArch.SELECTED_VARCH_COLOR_PALETTE);

            messageBox.setTitle("Palabra por ser consumida: " + pair.getRight());
        } else {
            clearAllMarks(vafnd);
            messageBox.setTitle("La palabra ha sido consumida");
        }

        vafnd.repaint();
    }

    private void clearAllMarks(VAFND vafnd) {
        vafnd.getVNodes().forEach(vnode -> vnode.setColorPalette(VNode.DEFAULT_VNODE_COLOR_PALETTE));
        vafnd.getVArchs().forEach(varch -> {
            varch.setColorPalette(VArch.DEFAULT_VARCH_COLOR_PALETTE);
            varch.getTriangle().setColorPalette(Triangle.VARCH_TRIANGLE_COLOR_PALETTE);
            vafnd.setVArchZIndex(varch, VAFND.MIN_LAYER);
        });
    }

    @Override public void clearState(AFND<String> afnd, VAFND vafnd, AFNDStateManager afndStateManager) {
        comprobado = false;
        palabraAceptada = false;
        recorridoIterator = null;
        afnd.clearRecorrido();
        vafnd.removeComponent(messageBox);
        clearAllMarks(vafnd);
        AFNDState.super.clearState(afnd, vafnd, afndStateManager);
    }

}
