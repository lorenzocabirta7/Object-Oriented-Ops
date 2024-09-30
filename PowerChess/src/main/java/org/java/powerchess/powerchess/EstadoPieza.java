package org.java.powerchess.powerchess;

public interface EstadoPieza {
    boolean verificarMovimiento(int xOrigen, int yOrigen, int xDestino, int yDestino, Tablero tablero);

    default boolean esRey() {
        return false;
    }

    default boolean esTorre() {
        return false;
    }

    default boolean esPeon() { return false; }
}
