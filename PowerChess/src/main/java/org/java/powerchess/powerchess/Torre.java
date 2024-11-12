package org.java.powerchess.powerchess;

import java.util.Objects;

public class Torre extends Pieza {

    public Torre(Color color) {
        super(color);
        this.nombre = "Torre";
    }

    @Override
    public boolean puedeCoronar(int yActual) {
        return false;
    }

    @Override
    public boolean verificarMovimiento(int xOrigen, int yOrigen, int xDestino, int yDestino, Tablero tablero) {
        if (xOrigen != xDestino && yOrigen != yDestino) {
            return false;
        }
        if (!tablero.hayObstaculosEntre(xOrigen, yOrigen, xDestino, yDestino)) {
            return tablero.casillaVacia(xDestino, yDestino) || tablero.hayPiezaEnemiga(xDestino, yDestino, tablero.obtenerPieza(xOrigen, yOrigen).getColor());
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
        Torre other = (Torre) obj;
        return (Objects.equals(this.getColor(), other.getColor()));
    }
}
