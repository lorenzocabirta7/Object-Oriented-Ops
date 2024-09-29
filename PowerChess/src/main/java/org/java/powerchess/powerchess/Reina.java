package org.java.powerchess.powerchess;

public class Reina implements EstadoPieza {
    private final EstadoPieza torre;
    private final EstadoPieza alfil;

    public Reina(EstadoPieza torre, EstadoPieza alfil) {
        this.torre = torre;
        this.alfil = alfil;
    }

    @Override
    public boolean verificarMovimiento(int xOrigen, int yOrigen, int xDestino, int yDestino, Tablero tablero) {

        if (torre.verificarMovimiento(xOrigen, yOrigen, xDestino, yDestino, tablero)) {
            return true;
        }

        if (alfil.verificarMovimiento(xOrigen, yOrigen, xDestino, yDestino, tablero)) {
            return true;
        }

        return false;
    }
}

