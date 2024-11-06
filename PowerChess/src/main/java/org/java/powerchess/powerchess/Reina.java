package org.java.powerchess.powerchess;

public class Reina extends Pieza {

    public Reina(Color color) {
        super(color);
        this.nombre = "Reina";
    }

    @Override
    public boolean verificarMovimiento(int xOrigen, int yOrigen, int xDestino, int yDestino, Tablero tablero) {
        /* TODO: IMPLEMENTAR EL MOVIMIENTO DE LA REINA  */
        /*if (torre.verificarMovimiento(xOrigen, yOrigen, xDestino, yDestino, tablero)) {
            return true;
        }

        if (alfil.verificarMovimiento(xOrigen, yOrigen, xDestino, yDestino, tablero)) {
            return true;
        }*/

        return false;
    }

}

