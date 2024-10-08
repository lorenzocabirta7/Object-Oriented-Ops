package org.java.powerchess.powerchess;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Tablero extends Observable {
    private List<List<Pieza>> casillas;
    private final int tamanio = 8;
    private Pair casillaSeleccionada = new Pair<>(-1,-1); // cuando las coordenadas son (-1, -1) no esta seleccionada

    public Tablero() {
        casillas = new ArrayList<>();
        for (int i = 0; i < tamanio; i++) {
            List<Pieza> fila = new ArrayList<>();
            for (int j = 0; j < tamanio; j++) {
                fila.add( null );
            }
            casillas.add(fila);
        }
        inicializarPiezas();
    }

    private void inicializarPiezas() {
        for (int i = 0; i < tamanio; i++) {
            casillas.get(i).set(1, new Pieza(Color.BLANCO, new Peon()));
            casillas.get(i).set(6, new Pieza(Color.NEGRO, new Peon()));
        }
        inicializarPiezasEspeciales(0, Color.BLANCO);
        inicializarPiezasEspeciales(7, Color.NEGRO);
    }

    private void inicializarPiezasEspeciales(int fila, Color color) {
        casillas.get(0).set(fila, new Pieza(color, new Torre()));
        casillas.get(7).set(fila, new Pieza(color, new Torre()));
        casillas.get(1).set(fila, new Pieza(color, new Caballo()));
        casillas.get(6).set(fila, new Pieza(color, new Caballo()));
        casillas.get(2).set(fila, new Pieza(color, new Alfil()));
        casillas.get(5).set(fila, new Pieza(color, new Alfil()));
        casillas.get(3).set(fila, new Pieza(color, new Reina(new Torre(), new Alfil())));
        casillas.get(4).set(fila, new Pieza(color, new Rey()));

    }

    private boolean piezaEnemigaEscudada(int xDestino, int yDestino){
        Pieza piezaEnemiga = obtenerPieza(xDestino, yDestino);
        return piezaEnemiga.estaProtegida();
    }

    public boolean moverPieza(int xOrigen, int yOrigen, int xDestino, int yDestino, Color color) {
        Pieza pieza;
        if ( ( pieza = this.obtenerPieza(xOrigen, yOrigen) ) == null) { return false; }
        if ( pieza.getColor() != color ) { return false; }

        if (pieza.mover(xOrigen, yOrigen, xDestino, yDestino, this)) {
            if ( hayPiezaEnemiga(xDestino, yDestino, color) && piezaEnemigaEscudada(xDestino, yDestino) ) return false;
            Pieza piezaDestino = obtenerPieza(xDestino, yDestino);
            if ( piezaDestino != null ) {
                if ( ( pieza.esTorre() && piezaDestino.esRey() ) || (pieza.esRey() && piezaDestino.esTorre() ) ) {
                    // TODO: enroque
                }
            }

            casillas.get(xDestino).set(yDestino, pieza);
            casillas.get(xOrigen).set(yOrigen, null);
            // TODO: si la pieza es un peon, y la casilla esta en la fila final, hay que coronarlo
            setChanged();
            return true;
        }
        return false;
    }

    public boolean casillaVacia(int x, int y) {
        return posicionDentroDelTablero(x, y) && casillas.get(x).get(y) == null;
    }

    public boolean casillaEstaSeleccionada(Pair<Integer, Integer> coordenadas) {
        return coordenadas.equals(this.casillaSeleccionada);
    }

    // Si la casilla estaba seleccionada, la deselecciona. Sino, la selecciona.
    public void cambiarSeleccionCasilla(Pair<Integer, Integer> coordenadas) {
        this.casillaSeleccionada = ( coordenadas.equals(this.casillaSeleccionada) ) ? new Pair(-1, -1) : coordenadas;
        setChanged();
        notifyObservers();
    }

    public boolean hayUnaCasillaSeleccionada() {
        return ! this.casillaSeleccionada.equals(new Pair(-1, -1));
    }

    public Pair<Integer, Integer> obtenerCasillaSeleccionada() { return this.casillaSeleccionada; }

    public boolean hayPiezaEnemiga(int x, int y, Color color) {
        if ( casillaVacia(x, y) ) return false;

        if (posicionDentroDelTablero(x, y)) {
            return casillas.get(x).get(y).getColor() != color;
        }
        return false;
    }

    public boolean posicionDentroDelTablero(int x, int y) {
        return x >= 0 && x < tamanio && y >= 0 && y < tamanio;
    }

    public Pieza obtenerPieza(int x, int y) {
        if (posicionDentroDelTablero(x, y)) {
            return casillas.get(x).get(y);
        }
        return null;
    }

    public boolean hayObstaculosEntre(int xOrigen, int yOrigen, int xDestino, int yDestino) {
        int deltaX = Integer.signum(xDestino - xOrigen);
        int deltaY = Integer.signum(yDestino - yOrigen);
        int x = xOrigen + deltaX;
        int y = yOrigen + deltaY;

        while (x != xDestino || y != yDestino) {
            if (!casillaVacia(x, y)) {
                return true;
            }
            x += deltaX;
            y += deltaY;
        }
        return false;
    }

    public Pieza encontrarRey(Color color) {
        for (List<Pieza> fila : casillas) {
            for (Pieza pieza : fila) {
                if (pieza != null && pieza.esRey() && pieza.getColor() == color) {
                    return pieza;
                }
            }
        }
        return null;
    }

    public boolean estaEnJaque(Color colorDelJugador) {
        Pieza rey = encontrarRey(colorDelJugador);
        if (rey == null) return false;

        int xRey = obtenerPosicion(rey, true);
        int yRey = obtenerPosicion(rey, false);

        for (List<Pieza> fila : casillas) {
            for (Pieza pieza : fila) {
                if (pieza != null && pieza.getColor() != colorDelJugador) {
                    if (pieza.mover(obtenerPosicion(pieza, true), obtenerPosicion(pieza, false), xRey, yRey, this)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean esJaqueMate(Color colorDelJugador) {
        if (!estaEnJaque(colorDelJugador)) {
            return false;
        }

        for (List<Pieza> fila : casillas) {
            for (Pieza pieza : fila) {
                if (pieza != null && pieza.getColor() == colorDelJugador) {
                    for (int x = 0; x < tamanio; x++) {
                        for (int y = 0; y < tamanio; y++) {
                            Pieza piezaDestino = casillas.get(x).get(y);
                            int xOrigen = obtenerPosicion(pieza, true);
                            int yOrigen = obtenerPosicion(pieza, false);

                            if (pieza.mover(xOrigen, yOrigen, x, y, this)) {
                                casillas.get(x).set(y, pieza);
                                casillas.get(xOrigen).set(yOrigen, null);

                                if (!estaEnJaque(colorDelJugador)) {
                                    casillas.get(xOrigen).set(yOrigen, pieza);
                                    casillas.get(x).set(y, piezaDestino);
                                    return false;
                                }

                                casillas.get(xOrigen).set(yOrigen, pieza);
                                casillas.get(x).set(y, piezaDestino);
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean puedeHacerEnroque(Color colorDelJugador, boolean enroqueCorto) {
        Pieza rey = encontrarRey(colorDelJugador);
        if (rey == null || rey.haSidoMovido()) return false;

        int filaRey = (colorDelJugador == Color.BLANCO) ? 0 : 7;
        int columnaRey = 4;
        int columnaTorre = enroqueCorto ? 7 : 0;
        int direccion = enroqueCorto ? 1 : -1;

        Pieza torre = casillas.get(columnaTorre).get(filaRey);

        if (!torre.esTorre() || torre.haSidoMovido()) {
            return false;
        }

        for (int i = columnaRey + direccion; i != columnaTorre; i += direccion) {
            if (casillas.get(i).get(filaRey) != null) return false;
        }

        for (int i = columnaRey; i != columnaRey + 2 * direccion; i += direccion) {
            if (simularMovimientoYVerificarJaque(rey, i, filaRey, colorDelJugador)) return false;
        }

        return true;
    }

    public boolean hacerEnroque(Color colorDelJugador, boolean enroqueCorto) {
        if (puedeHacerEnroque(colorDelJugador, enroqueCorto)) {
            Pieza rey = encontrarRey(colorDelJugador);
            int filaRey = (colorDelJugador == Color.BLANCO) ? 0 : 7;
            int columnaRey = 4;
            int direccion = enroqueCorto ? 1 : -1;

            casillas.get(columnaRey + 2 * direccion).set(filaRey, rey);
            casillas.get(columnaRey).set(filaRey, null);

            int columnaTorre = enroqueCorto ? 7 : 0;
            Pieza torre = casillas.get(columnaTorre).get(filaRey);
            casillas.get(columnaRey + direccion).set(filaRey, torre);
            casillas.get(columnaTorre).set(filaRey, null);

            setChanged();
            return true;
        }
        return false;
    }

    public int obtenerPosicion(Pieza pieza, boolean obtenerX) {
        for (int x = 0; x < tamanio; x++) {
            for (int y = 0; y < tamanio; y++) {
                if (casillas.get(x).get(y) == pieza) {
                    return obtenerX ? x : y;
                }
            }
        }
        return -1;
    }

    private boolean simularMovimientoYVerificarJaque(Pieza pieza, int xDestino, int yDestino, Color colorDelJugador) {
        int xOrigen = obtenerPosicion(pieza, true);
        int yOrigen = obtenerPosicion(pieza, false);
        Pieza piezaDestinoOriginal = casillas.get(xDestino).get(yDestino);

        casillas.get(xDestino).set(yDestino, pieza);
        casillas.get(xOrigen).set(yOrigen, null);

        boolean sigueEnJaque = estaEnJaque(colorDelJugador);

        casillas.get(xOrigen).set(yOrigen, pieza);
        casillas.get(xDestino).set(yDestino, piezaDestinoOriginal);

        return sigueEnJaque;
    }
}
