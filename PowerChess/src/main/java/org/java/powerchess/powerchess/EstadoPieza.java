package org.java.powerchess.powerchess;

public interface EstadoPieza {
    boolean verificarMovimiento(int xOrigen, int yOrigen, int xDestino, int yDestino, Tablero tablero);

    default boolean esRey() {
        return false;
    }

    // se utiliza para cargar la imagen en el tablero
    String getNombre();

    default boolean esTorre() {
        return false;
    }

    default boolean esPeon() { return false; }
}
