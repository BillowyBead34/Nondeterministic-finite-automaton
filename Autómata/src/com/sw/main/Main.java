package com.sw.main;

import com.sw.controller.SetupManager;
import com.sw.view.MainView;
import java.awt.EventQueue;

/**
 *
 * @author HikingCarrot7
 */
public class Main {

    public static void main(String[] args) {
	EventQueue.invokeLater(() -> {
	    MainView view = new MainView();
	    view.setLocationRelativeTo(null);
	    view.setVisible(true);
	    new SetupManager(view);
	});
    }
}
