package org.java.powerchess.powerchess;

public class Reina extends Pieza {

    public Reina(Color color) {
        super(color);
        this.nombre = "Reina";
    }

    @Override
    public boolean puedeCoronar(int yActual) {
        return false;
    }

    @Override
    public boolean verificarMovimiento(int xOrigen, int yOrigen, int xDestino, int yDestino, Tablero tablero) {
        if ( (xOrigen != xDestino && yOrigen != yDestino) && (Math.abs(xDestino - xOrigen) != Math.abs(yDestino - yOrigen)) ) {
            // Si no se mueve en linea recta ni en diagonal, devuelvo false
            return false;
        }

        if (!tablero.hayObstaculosEntre(xOrigen, yOrigen, xDestino, yDestino)) {
            return tablero.casillaVacia(xDestino, yDestino) || tablero.hayPiezaEnemiga(xDestino, yDestino, tablero.obtenerPieza(xOrigen, yOrigen).getColor());
        }

        return false;
    }

}

