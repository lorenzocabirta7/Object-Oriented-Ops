package org.java.powerchess.powerchess;

public interface EstadoPieza {
    boolean verificarMovimiento(int xOrigen, int yOrigen, int xDestino, int yDestino, Tablero tablero);
}
