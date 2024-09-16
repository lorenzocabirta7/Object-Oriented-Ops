package org.java.powerchess.powerchess;

public class Peon implements EstadoPieza {
    private boolean primerMovimiento = true;

    @Override
    public boolean verificarMovimiento(int xOrigen, int yOrigen, int xDestino, int yDestino, Tablero tablero) {
        int direccion = (tablero.obtenerPieza(xOrigen, yOrigen).getColor() == Blanco.BLANCO) ? 1 : -1; //Dirección basada en el color del peon

        //Movimiento simple hacia adelante
        if (xDestino == xOrigen && yDestino == yOrigen + direccion) {
            if (tablero.casillaVacia(xDestino, yDestino)) {
                primerMovimiento = false;
                return true;
            }
        }

        //Primer movimiento dos casillas para adelante
        if (primerMovimiento && xDestino == xOrigen && yDestino == yOrigen + 2 * direccion) {
            if (tablero.casillaVacia(xDestino, yDestino) && tablero.casillaVacia(xOrigen, yOrigen + direccion)) {
                primerMovimiento = false;
                return true;
            }
        }

        //Comer en diagonal
        if (Math.abs(xDestino - xOrigen) == 1 && yDestino == yOrigen + direccion) {
            if (tablero.hayPiezaEnemiga(xDestino, yDestino, tablero.obtenerPieza(xOrigen, yOrigen).getColor())) {
                primerMovimiento = false;
                return true;
            }
        }

        return false;
    }
}
