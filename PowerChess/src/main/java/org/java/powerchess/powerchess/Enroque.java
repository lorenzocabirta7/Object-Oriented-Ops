package org.java.powerchess.powerchess;

import javafx.util.Pair;

public class Enroque {

    private boolean _esEnroqueCorto(int xTorre, int xRey) {
        return Math.abs( xTorre - xRey ) == 3 ;
    }

    /* Chequea que las piezas no sean nulas, que sean Torre y Rey, y que sean del mismo color */
    private boolean _verificarPiezasYColores(Pieza piezaOrigen, Pieza piezaDestino) {
        if (piezaOrigen == null || piezaDestino == null || piezaOrigen.getColor() != piezaDestino.getColor()) {
            return false;
        }

        Color colorDelJugador = piezaOrigen.getColor();

        if ( piezaOrigen.equals(new Torre(colorDelJugador)) && piezaDestino.equals(new Rey(colorDelJugador)) ) {

            return true;
        }

        if (piezaOrigen.equals(new Rey(colorDelJugador)) && piezaDestino.equals(new Torre(colorDelJugador)) ) {
            return true;
        }

        return false;
    }

    private boolean _puedoHacerEnroque(int xTorre, int yTorre, int xRey, int yRey, Tablero tablero) {

        Torre torre = (Torre) tablero.obtenerPieza(xTorre, yTorre);
        Rey rey = (Rey) tablero.obtenerPieza(xRey, yRey);
        Color colorDelJugador = torre.getColor();

        if (rey.haSidoMovido() || torre.haSidoMovido() || rey.verSiEstaEnJaque(tablero)) return false;

        if ( tablero.hayObstaculosEntre(xRey, yRey, xTorre, yTorre) ) return false;

        boolean esEnroqueCorto = _esEnroqueCorto(xTorre, xRey);
        int direccion = esEnroqueCorto ? -1 : 1;
        int destino = xRey + 2 * direccion;

        for (int i = xRey; i != destino + direccion; i += direccion) {
            if ( !tablero.simularMovimientoYVerificarQueNoEstaEnJaque(rey, i, yRey, colorDelJugador, rey) ) return false;
        }

        return true;
    }

    /* Si puedo enrocar, devuelve las posiciones en el eje x de la Torre y del Rey, en el orden
    en que fueron pasadas las piezas. En caso contrario devuelve null */
    public Pair<Integer, Integer> hacerEnroque(Pieza piezaOrigen, Pieza piezaDestino, Tablero tablero) {

        if ( ! _verificarPiezasYColores(piezaOrigen, piezaDestino) ) return null;
        Color colorDelJugador = piezaOrigen.getColor();
        boolean piezaDeOrigenEsTorre = piezaOrigen.equals(new Torre(colorDelJugador));
        Pieza piezaTorre = piezaDeOrigenEsTorre ? piezaOrigen : piezaDestino;
        Pieza piezaRey = piezaDeOrigenEsTorre ? piezaDestino : piezaOrigen;

        int xTorre = tablero.obtenerPosicion(piezaTorre, true);
        int yTorre = tablero.obtenerPosicion(piezaTorre, false);
        int xRey = tablero.obtenerPosicion(piezaRey, true);
        int yRey = tablero.obtenerPosicion(piezaRey, false);

        if ( ! _puedoHacerEnroque(xTorre, yTorre, xRey, yRey, tablero) ) return null;

        boolean enroqueCorto = _esEnroqueCorto(xTorre, xRey);
        int direccion = enroqueCorto ? -1 : 1;
        int xTorreFinal = xRey + direccion;
        int xReyFinal = xRey + 2 * direccion;

        return piezaDeOrigenEsTorre ? new Pair<>(xTorreFinal, xReyFinal) : new Pair<>(xReyFinal, xTorreFinal);

    }
}
