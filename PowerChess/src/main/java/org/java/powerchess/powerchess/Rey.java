package org.java.powerchess.powerchess;

public class Rey implements EstadoPieza {
    private final String nombre = "Rey";

    @Override
    public boolean verificarMovimiento(int xOrigen, int yOrigen, int xDestino, int yDestino, Tablero tablero) {
        int deltaX = Math.abs(xDestino - xOrigen);
        int deltaY = Math.abs(yDestino - yOrigen);

        return (deltaX <= 1 && deltaY <= 1) && !(deltaX == 0 && deltaY == 0)
                && (tablero.casillaVacia(xDestino, yDestino) || tablero.hayPiezaEnemiga(xDestino, yDestino, tablero.obtenerPieza(xOrigen, yOrigen).getColor()));
    }

    @Override
    public boolean esRey() {
        return true;
    }

    public String getNombre() {
        return this.nombre;
    }
}
