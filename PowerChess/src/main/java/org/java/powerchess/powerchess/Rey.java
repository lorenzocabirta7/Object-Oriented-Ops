package org.java.powerchess.powerchess;

import java.util.List;
import java.util.Objects;

public class Rey extends Pieza {

    public Rey(Color color) {
        super(color);
        this.nombre = "Rey";
    }

    @Override
    public boolean puedeCoronar(int yActual) {
        return false;
    }

    @Override
    public boolean verificarMovimiento(int xOrigen, int yOrigen, int xDestino, int yDestino, Tablero tablero) {
        int deltaX = Math.abs(xDestino - xOrigen);
        int deltaY = Math.abs(yDestino - yOrigen);

        return (deltaX <= 1 && deltaY <= 1) && !(deltaX == 0 && deltaY == 0)
                && (tablero.casillaVacia(xDestino, yDestino) || tablero.hayPiezaEnemiga(xDestino, yDestino, tablero.obtenerPieza(xOrigen, yOrigen).getColor()));
    }

    public boolean verSiEstaEnJaque(Tablero tablero) {
        int xRey = tablero.obtenerPosicion(this, true);
        int yRey = tablero.obtenerPosicion(this, false);

        List<Pieza> piezasEnemigas = tablero.obtenerPiezasEnemigas(this.getColor());
        for ( Pieza pieza : piezasEnemigas ) {
            // aca quiero ver si cada pieza enemiga esta atacando al rey
            int xPiezaEnemiga = tablero.obtenerPosicion(pieza, true);
            int yPiezaEnemiga = tablero.obtenerPosicion(pieza, false);

            if ( pieza.mover(xPiezaEnemiga, yPiezaEnemiga, xRey, yRey, tablero) ) {
                return true;
            }
        }

        return false;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Rey other = (Rey) obj;
        return (Objects.equals(this.getColor(), other.getColor()));
    }
}
