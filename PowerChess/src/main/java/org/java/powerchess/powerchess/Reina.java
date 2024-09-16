package org.java.powerchess.powerchess;

public class Reina implements EstadoPieza {

    @Override
    public boolean verificarMovimiento(int xOrigen, int yOrigen, int xDestino, int yDestino, Tablero tablero) {
        //Usa los movimiento de torres y alfiles
        Torre torre = new Torre();
        Alfil alfil = new Alfil();

        return torre.verificarMovimiento(xOrigen, yOrigen, xDestino, yDestino, tablero) ||
                alfil.verificarMovimiento(xOrigen, yOrigen, xDestino, yDestino, tablero);
    }
}
