package org.java.powerchess.powerchess;

public class Caballo implements EstadoPieza {
    private final String nombre = "Caballo";
    @Override
    public boolean verificarMovimiento(int xOrigen, int yOrigen, int xDestino, int yDestino, Tablero tablero) {
        int deltaX = Math.abs(xDestino - xOrigen);
        int deltaY = Math.abs(yDestino - yOrigen);

        if ((deltaX == 2 && deltaY == 1) || (deltaX == 1 && deltaY == 2)) {
            return tablero.casillaVacia(xDestino, yDestino) || tablero.hayPiezaEnemiga(xDestino, yDestino, tablero.obtenerPieza(xOrigen, yOrigen).getColor());
        }
        return false;
    }

    public String getNombre() {
        return this.nombre;
    }
}
