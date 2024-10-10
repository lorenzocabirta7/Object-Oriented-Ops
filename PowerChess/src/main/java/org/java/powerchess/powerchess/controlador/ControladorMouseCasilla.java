package org.java.powerchess.powerchess.controlador;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;
import org.java.powerchess.powerchess.Juego;
import org.java.powerchess.powerchess.Jugador;
import org.java.powerchess.powerchess.Pieza;
import org.java.powerchess.powerchess.Tablero;

public class ControladorMouseCasilla implements EventHandler<MouseEvent> {
    private Juego juego;
    private Pair<Integer,Integer> casillaActual;

    public ControladorMouseCasilla(Juego juego, Pair<Integer, Integer> coordenadas) {
        this.juego = juego;
        this.casillaActual = coordenadas;
    }

    @Override
    public void handle(MouseEvent e) {
        // la casilla seleccionada sigue siendo la previa (o (-1,-1) )
        Tablero tablero = this.juego.obtenerTablero();
        Jugador jugadorActual = this.juego.obtenerJugadorActual();
        
        if ( jugadorActual.hayUnPoderSeleccionado() ) {
          Pieza pieza = tablero.obtenerPieza(casillaActual.getKey(), casillaActual.getValue());
          jugadorActual.activarPoder(jugadorActual.obtenerPoderSeleccionado(), pieza);
          jugadorActual.cambiarSeleccionPoder(null);
        }

        if ( tablero.hayUnaCasillaSeleccionada() ) {
            Pair<Integer, Integer> casillaPrevia = tablero.obtenerCasillaSeleccionada();
            this.juego.moverPieza(casillaPrevia.getKey(), casillaPrevia.getValue(), casillaActual.getKey(), casillaActual.getValue());
            tablero.cambiarSeleccionCasilla(casillaActual);
        }
        else {
            Pieza pieza = tablero.obtenerPieza(casillaActual.getKey(), casillaActual.getValue());
            if ( pieza != null && juego.obtenerColorDelJugadorActual().equals(pieza.getColor()) ) {
                tablero.cambiarSeleccionCasilla(casillaActual);
            }

        }
    }
}
