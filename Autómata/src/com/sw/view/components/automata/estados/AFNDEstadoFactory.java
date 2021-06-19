package com.sw.view.components.automata.estados;

import com.sw.view.components.VNode;
import java.awt.Point;

/**
 *
 * @author HikingCarrot7
 */
public interface AFNDEstadoFactory {

    public VNode createEstado(int id, String data, Point centerCoords);

}
