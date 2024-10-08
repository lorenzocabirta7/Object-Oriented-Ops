package org.java.powerchess.powerchess.controlador;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;
import org.java.powerchess.powerchess.Juego;
import org.java.powerchess.powerchess.Pieza;
import org.java.powerchess.powerchess.Tablero;

public class ControladorMouse implements EventHandler<MouseEvent> {
    private Juego juego;
    private Pair<Integer,Integer> casillaActual;

    public ControladorMouse(Juego juego, Pair<Integer, Integer> coordenadas) {
        this.juego = juego;
        this.casillaActual = coordenadas;
    }

    @Override
    public void handle(MouseEvent e) {
        // la casilla seleccionada sigue siendo la previa (o (-1,-1) )
        Tablero tablero = this.juego.obtenerTablero();
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
        // aca se actualiza. TODO: esto deberia ser solo si la pieza le corresponde al jugador


    }
}
