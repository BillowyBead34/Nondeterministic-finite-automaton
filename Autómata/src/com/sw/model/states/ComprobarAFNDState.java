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
import java.util.List;

/**
 *
 * @author HikingCarrot7
 */
public class ComprobarAFNDState implements AFNDState {

    private static ComprobarAFNDState instance;

    public synchronized static ComprobarAFNDState getInstance() {
        if (instance == null)
            instance = new ComprobarAFNDState();

        return instance;
    }

    private boolean comprobado;
    private final TextBox messageBox;

    private ComprobarAFNDState() {
        messageBox = new TextBox.TextBoxBuilder()
                .setBoxPosition(Box.BoxPosition.TOP_LEFT)
                .build();
    }

    @Override public void updateGraphState(AFND<String> afnd, VAFND vafnd, AFNDStateManager afndStateManager, InputEvent event, int buttonID) {
        if (!comprobado)
            comprobarAFND(afnd, vafnd);

        vafnd.repaint();
    }

    private void comprobarAFND(AFND<String> afnd, VAFND vafnd) {
        String text = Menu.TEXT_FIELD.getText();
        try {
            boolean matches = afnd.matches(text);
            vafnd.addComponent(messageBox, VAFND.MAX_LAYER);

            if (matches) {
                messageBox.setTitle("La palabra FUE ACEPTADA por el autómata");
                messageBox.setColorPalette(TextBox.GREEN_TEXTBOX_COLOR_PALETTE);
                pintarRecorrido(vafnd, afnd.getRecorrido());
            } else {
                messageBox.setTitle("La palabra NO FUE ACEPTADA por el autómata");
                messageBox.setColorPalette(TextBox.RED_TEXTBOX_COLOR_PALETTE);
            }

        } catch (CadenaVaciaException | NoExisteEstadoInicialException e) {
            vafnd.getDefaultTextBox().setTitle(e.getMessage());
            vafnd.repaint();
        }

        comprobado = true;
    }

    private void pintarRecorrido(VAFND vafnd, List<Pair<Arch<?>, String>> recorrido) {
        recorrido.forEach(pair -> {
            Arch<?> arch = pair.getLeft();

            VNode origen = vafnd.getVNode(arch.getOrigen().getName().toString());
            VNode destino = vafnd.getVNode(arch.getDestino().getName().toString());
            VArch varch = vafnd.getVArch(origen, destino);

            origen.setColorPalette(VNode.SELECTED_RUTA_VNODE_COLOR_PALETTE);
            destino.setColorPalette(VNode.SELECTED_RUTA_VNODE_COLOR_PALETTE);
            varch.setColorPalette(VArch.SELECTED_VARCH_COLOR_PALETTE);
            varch.getTriangle().setColorPalette(VArch.SELECTED_VARCH_COLOR_PALETTE);
        });
    }

    @Override public void clearState(AFND<String> afnd, VAFND vafnd, AFNDStateManager afndStateManager) {
        vafnd.getVNodes().forEach(vnode -> vnode.setColorPalette(VNode.DEFAULT_VNODE_COLOR_PALETTE));
        vafnd.getVArchs().forEach(varch -> {
            varch.setColorPalette(VArch.DEFAULT_VARCH_COLOR_PALETTE);
            varch.getTriangle().setColorPalette(Triangle.VARCH_TRIANGLE_COLOR_PALETTE);
            vafnd.setVArchZIndex(varch, VAFND.MIN_LAYER);
        });

        comprobado = false;
        afnd.clearRecorrido();
        vafnd.removeComponent(messageBox);
        AFNDState.super.clearState(afnd, vafnd, afndStateManager);
    }

}
