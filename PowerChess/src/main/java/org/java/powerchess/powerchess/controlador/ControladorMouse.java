package org.java.powerchess.powerchess.controlador;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;
import org.java.powerchess.powerchess.Tablero;

public class ControladorMouse implements EventHandler<MouseEvent> {
    private Tablero tablero;
    private Pair<Integer,Integer> casillaActual;

    public ControladorMouse(Tablero tablero, Pair<Integer, Integer> coordenadas) {
        this.tablero = tablero;
        this.casillaActual = coordenadas;
    }

    @Override
    public void handle(MouseEvent e) {
        tablero.cambiarSeleccionCasilla(casillaActual);
    }
}
