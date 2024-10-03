package org.java.powerchess.powerchess;

public class Alfil implements EstadoPieza {
    private final String nombre = "Alfil";
    @Override
    public boolean verificarMovimiento(int xOrigen, int yOrigen, int xDestino, int yDestino, Tablero tablero) {
        if (Math.abs(xDestino - xOrigen) != Math.abs(yDestino - yOrigen)) {
            return false;
        }

        if (!tablero.hayObstaculosEntre(xOrigen, yOrigen, xDestino, yDestino)) {
            return tablero.casillaVacia(xDestino, yDestino) ||
                    tablero.hayPiezaEnemiga(xDestino, yDestino, tablero.obtenerPieza(xOrigen, yOrigen).getColor());
        }

        return false;
    }

    public String getNombre() {
        return this.nombre;
    }
}
