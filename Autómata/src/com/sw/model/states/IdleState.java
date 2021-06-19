package com.sw.model.states;

import com.sw.model.automata.AFND;
import com.sw.view.components.automata.VAFND;
import java.awt.event.InputEvent;

/**
 *
 * @author HikingCarrot7
 */
public class IdleState implements AFNDState {

    private static IdleState instance;

    public synchronized static IdleState getInstance() {
        if (instance == null)
            instance = new IdleState();

        return instance;
    }

    private IdleState() {
    }

    @Override public void updateGraphState(AFND<String> graph, VAFND vgraph, AFNDStateManager graphStateManager, InputEvent event, int buttonID) {

    }
}
