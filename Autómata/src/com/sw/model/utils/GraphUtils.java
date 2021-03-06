package com.sw.model.utils;

import com.sw.model.automata.AFND;
import com.sw.view.components.VArch;
import com.sw.view.components.VNode;
import com.sw.view.components.automata.VAFND;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HikingCarrot7
 */
public class GraphUtils {

    private GraphUtils() {
    }

    public static int getVerticePresionado(AFND<?> afnd, List<VNode> vnodes, Point mouse) {
        for (int i = 0; i < afnd.getNumeroVertices(); i++) {
            Point center = new Point(vnodes.get(i).getXCenter(), vnodes.get(i).getYCenter());
            if (MathHelper.distanciaEntreDosPuntos(center, mouse) <= VNode.RADIO_NODO)
                return i;
        }

        return -1;
    }

    public static List<VArch> getAdjacentVArchs(VNode origen, VAFND vafnd) {
        List<VArch> adjacentVArchs = new ArrayList<>();
        List<VArch> varchs = vafnd.getVArchs();

        varchs.forEach(varch -> {
            if (origen == varch.getOrigen())
                adjacentVArchs.add(varch);
        });

        return adjacentVArchs;
    }
}
