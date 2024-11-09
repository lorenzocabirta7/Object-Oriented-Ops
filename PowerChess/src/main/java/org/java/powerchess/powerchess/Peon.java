package org.java.powerchess.powerchess;

public class Peon extends Pieza {
    private boolean primerMovimiento = true;

    public Peon(Color color) {
        super(color);
        this.nombre = "Peon";
    }

    @Override
    public boolean puedeCoronar(int yActual) {
        return (yActual == 0 || yActual == 7);
    }

    @Override
    public boolean verificarMovimiento(int xOrigen, int yOrigen, int xDestino, int yDestino, Tablero tablero) {
        int direccion = (tablero.obtenerPieza(xOrigen, yOrigen).getColor() == Color.BLANCO) ? 1 : -1;

        //Mover adelante
        if (xDestino == xOrigen && yDestino == yOrigen + direccion) {
            if (tablero.casillaVacia(xDestino, yDestino)) {
                primerMovimiento = false;
                return true;
            }
        }

        //Primer movimiento de dos casillas
        if (primerMovimiento && xDestino == xOrigen && yDestino == yOrigen + 2 * direccion) {
            if (tablero.casillaVacia(xDestino, yDestino) && tablero.casillaVacia(xOrigen, yOrigen + direccion)) {
                primerMovimiento = false;
                return true;
            }
        }

        //Comer
        if (Math.abs(xDestino - xOrigen) == 1 && yDestino == yOrigen + direccion) {
            if (tablero.hayPiezaEnemiga(xDestino, yDestino, tablero.obtenerPieza(xOrigen, yOrigen).getColor())) {
                primerMovimiento = false;
                return true;
            }
        }

        return false;
    }

}
