package org.java.powerchess.powerchess;

public class Rey extends Pieza {

    public Rey(Color color) {
        super(color);
        this.nombre = "Rey";
    }

    @Override
    public boolean verificarMovimiento(int xOrigen, int yOrigen, int xDestino, int yDestino, Tablero tablero) {
        int deltaX = Math.abs(xDestino - xOrigen);
        int deltaY = Math.abs(yDestino - yOrigen);

        return (deltaX <= 1 && deltaY <= 1) && !(deltaX == 0 && deltaY == 0)
                && (tablero.casillaVacia(xDestino, yDestino) || tablero.hayPiezaEnemiga(xDestino, yDestino, tablero.obtenerPieza(xOrigen, yOrigen).getColor()));
    }

}
