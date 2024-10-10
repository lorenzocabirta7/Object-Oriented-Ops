package org.java.powerchess.powerchess.controlador;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;
import org.java.powerchess.powerchess.Juego;
import org.java.powerchess.powerchess.Jugador;
import org.java.powerchess.powerchess.Poder;
import org.java.powerchess.powerchess.Tablero;

public class ControladorMousePoderes implements EventHandler<MouseEvent> {
    private Juego juego;
    private Poder poderActual;

    public ControladorMousePoderes(Juego juego, Poder poder) {
        this.juego = juego;
        this.poderActual = poder;
    }

    @Override
    public void handle(MouseEvent e) {
        Jugador jugador = this.juego.obtenerJugadorActual();
        Tablero tablero = this.juego.obtenerTablero();

        jugador.cambiarSeleccionPoder(poderActual);

        if ( tablero.hayUnaCasillaSeleccionada() ) {
          tablero.cambiarSeleccionCasilla(new Pair(-1, -1));
      }
    }
}
