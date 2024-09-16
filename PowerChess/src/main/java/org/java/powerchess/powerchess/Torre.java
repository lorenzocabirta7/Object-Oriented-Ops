package org.java.powerchess.powerchess;

public class Torre implements EstadoPieza {

    @Override
    public boolean verificarMovimiento(int xOrigen, int yOrigen, int xDestino, int yDestino, Tablero tablero) {
        if (xOrigen != xDestino && yOrigen != yDestino) {
            return false; //Checkea movimiento invalido
        }

        //Verificacion para ver que no haya piezas en el camino
        if (!tablero.hayObstaculosEntre(xOrigen, yOrigen, xDestino, yDestino)) {
            //Verificar si el destino está vacio o contiene una pieza enemiga
            return tablero.casillaVacia(xDestino, yDestino) || tablero.hayPiezaEnemiga(xDestino, yDestino, tablero.obtenerPieza(xOrigen, yOrigen).getColor());
        }

        return false;
    }
}
