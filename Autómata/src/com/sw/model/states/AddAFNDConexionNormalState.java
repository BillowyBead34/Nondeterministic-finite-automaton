package com.sw.model.states;

import com.sw.model.automata.AFND;
import com.sw.model.utils.GraphUtils;
import com.sw.view.components.DialogueBalloon;
import com.sw.view.components.TextTyper;
import com.sw.view.components.VArch;
import com.sw.view.components.VNode;
import com.sw.view.components.automata.VAFND;
import com.sw.view.components.automata.conexiones.ConexionNormal;
import com.sw.view.graphics.Movable;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 *
 * @author HikingCarrot7
 */
public class AddAFNDConexionNormalState implements AFNDState {

    private static AddAFNDConexionNormalState instance;

    public synchronized static AddAFNDConexionNormalState getInstance() {
        if (instance == null)
            instance = new AddAFNDConexionNormalState();

        return instance;
    }

    private AddAFNDConexionNormalState() {
    }

    private boolean insertandoCondicion;
    private VNode origen;
    private VNode destino;
    private Movable cursor;
    private VArch previewArch;
    private VArch previousArch;
    private TextTyper textTyper;
    private DialogueBalloon dialogueballoon;

    @Override public void updateGraphState(AFND<String> afnd, VAFND vafnd, AFNDStateManager afndStateManager, InputEvent event, int buttonID) {
        if (event instanceof MouseEvent) {
            MouseEvent mouseEvent = (MouseEvent) event;
            if (event.getID() == MouseEvent.MOUSE_CLICKED) {
                if (mouseEvent.getButton() == MouseEvent.BUTTON1)
                    if (origenSeleccionado())
                        selectDestino(afnd, vafnd, mouseEvent);
                    else
                        selectOrigen(afnd, vafnd, mouseEvent);
                else
                    clearState(afnd, vafnd, afndStateManager);

                vafnd.repaint();
            }

            if (event.getID() == MouseEvent.MOUSE_MOVED) {
                updateArchPreview(vafnd, mouseEvent);
                vafnd.repaint();
            }
        }

        if (event.getID() == KeyEvent.KEY_PRESSED) {
            KeyEvent keyEvent = (KeyEvent) event;
            if (insertandoCondicion && keyEvent.getKeyCode() == KeyEvent.VK_ENTER)
                if (addArch(afnd, vafnd))
                    clearState(afnd, vafnd, afndStateManager);
                else {
                    dialogueballoon.setText("El valor es inv??lido");
                    vafnd.repaint();
                }

            if (insertandoCondicion)
                insertarEstado(vafnd, keyEvent);
        }

    }

    private void selectOrigen(AFND<String> afnd, VAFND vafnd, MouseEvent e) {
        if (origen == null) {
            int verticePresionado = GraphUtils.getVerticePresionado(afnd, vafnd.getVNodes(), e.getPoint());
            if (verticePresionado >= 0 && afnd.getNumeroVertices() > 1) {
                origen = vafnd.getVNode(verticePresionado);
                origen.setColorPalette(VNode.SELECTED_VNODE_COLOR_PALETTE);
                vafnd.getDefaultTextBox().setTitle("Da click derecho a otro estado para crear una conexi??n.");
            }
        }
    }

    private void selectDestino(AFND<String> afnd, VAFND vafnd, MouseEvent e) {
        int nVerticePresionado = GraphUtils.getVerticePresionado(afnd, vafnd.getVNodes(), e.getPoint());

        if (nVerticePresionado >= 0) {
            destino = vafnd.getVNode(nVerticePresionado);

            if (destino != origen) {
                if (afnd.existeConexion(origen.getName(), destino.getName())) {
                    previousArch = vafnd.getVArch(origen, destino);
                    afnd.removeArch(origen.getName(), destino.getName());
                    vafnd.removeVArch(previousArch);
                }

                destino = vafnd.getVNode(nVerticePresionado);
                previewArch.setDestino(destino);
                previewArch.setPreviewMode(false);

                textTyper = new TextTyper(previewArch.getBlob().getCoords(), 1);
                dialogueballoon = new DialogueBalloon(vafnd, previewArch.getBlob(), "Inserte la condici??n");
                insertandoCondicion = true;

                vafnd.addComponent(dialogueballoon, VAFND.MIDDLE_LAYER);
                vafnd.getDefaultTextBox().setTitle("As??gnale una condici??n a la conexi??n.");
            }
        }
    }

    private void updateArchPreview(VAFND vafnd, MouseEvent e) {
        if (origen != null)
            if (cursor == null) {
                cursor = new VNode("CURSOR_PREVIEW", e.getPoint());
                previewArch = new ConexionNormal(origen, cursor, true);
                vafnd.addVArch(previewArch, VAFND.MIN_LAYER);
            } else {
                cursor.setXCenter(e.getX());
                cursor.setYCenter(e.getY());
            }
    }

    private void insertarEstado(VAFND vafnd, KeyEvent keyEvent) {
        textTyper.handleInputEvent(keyEvent);
        previewArch.setCondicion(textTyper.getText());
        vafnd.repaint();
    }

    private boolean addArch(AFND<String> afnd, VAFND vafnd) {
        if (textTyper.getText().isEmpty())
            return false;

        String text = textTyper.getText();

        afnd.addArch(origen.getName(), destino.getName(), text);
        vafnd.addVArch(new ConexionNormal(origen, destino, text), VAFND.MIN_LAYER);
        previousArch = null;
        return true;
    }

    private boolean origenSeleccionado() {
        return origen != null;
    }

    @Override public void clearState(AFND<String> afnd, VAFND vafnd, AFNDStateManager afndStateManager) {
        if (previousArch != null) {
            afnd.addArch(
                    ((VNode) previousArch.getOrigen()).getName(),
                    ((VNode) previousArch.getDestino()).getName(),
                    previousArch.getCondicion());

            vafnd.addVArch(previousArch, VAFND.MIN_LAYER);
        }

        vafnd.removeVNode((VNode) cursor);

        if (previewArch != null)
            vafnd.removeVArch(previewArch);

        if (origen != null)
            ((VNode) origen).setColorPalette(VNode.DEFAULT_VNODE_COLOR_PALETTE);

        vafnd.removeComponent(dialogueballoon);

        cursor = null;
        origen = null;
        destino = null;
        previewArch = null;
        dialogueballoon = null;
        insertandoCondicion = false;
        AFNDState.super.clearState(afnd, vafnd, afndStateManager);
    }

}
