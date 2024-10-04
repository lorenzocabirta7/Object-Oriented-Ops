package org.java.powerchess.powerchess.controlador;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import org.java.powerchess.powerchess.Celda;
import org.java.powerchess.powerchess.Tablero;

public class ControladorMouse implements EventHandler<MouseEvent> {
    private Celda celda;

    public ControladorMouse(Celda celda) {
        this.celda = celda;
    }

    @Override
    public void handle(MouseEvent e) {
        celda.cambiarSeleccion();
    }
}
