package org.java.powerchess.powerchess;

import java.util.ArrayList;
import java.util.List;

public class Tablero {
    private List<List<Pieza>> casillas;
    private final int tamanio = 8;

    public Tablero() {
        casillas = new ArrayList<>();
        for (int i = 0; i < tamanio; i++) {
            List<Pieza> fila = new ArrayList<>();
            for (int j = 0; j < tamanio; j++) {
                fila.add(null);
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

    public boolean moverPieza(Pieza pieza, int xOrigen, int yOrigen, int xDestino, int yDestino) {
        if (pieza.mover(xOrigen, yOrigen, xDestino, yDestino, this) && !piezaEnemigaEscudada(xDestino, yDestino)) {
            casillas.get(xDestino).set(yDestino, pieza); //comer
            casillas.get(xOrigen).set(yOrigen, null);
            return true;
        }
        return false;
    }

    public boolean casillaVacia(int x, int y) {
        return posicionDentroDelTablero(x, y) && casillas.get(x).get(y) == null;
    }

    public boolean hayPiezaEnemiga(int x, int y, Color color) {
        if (posicionDentroDelTablero(x, y)) {
            Pieza pieza = casillas.get(x).get(y);
            return pieza != null && pieza.getColor() != color;
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

    public boolean estaEnJaque(Jugador jugador) {
        Pieza rey = encontrarRey(jugador.getColor());
        if (rey == null) return false;

        int xRey = obtenerPosicion(rey, true);
        int yRey = obtenerPosicion(rey, false);

        for (List<Pieza> fila : casillas) {
            for (Pieza pieza : fila) {
                if (pieza != null && pieza.getColor() != jugador.getColor()) {
                    if (pieza.mover(obtenerPosicion(pieza, true), obtenerPosicion(pieza, false), xRey, yRey, this)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean esJaqueMate(Jugador jugador) {
        if (!estaEnJaque(jugador)) {
            return false;
        }

        for (List<Pieza> fila : casillas) {
            for (Pieza pieza : fila) {
                if (pieza != null && pieza.getColor() == jugador.getColor()) {
                    for (int x = 0; x < tamanio; x++) {
                        for (int y = 0; y < tamanio; y++) {
                            Pieza piezaDestino = casillas.get(x).get(y);
                            int xOrigen = obtenerPosicion(pieza, true);
                            int yOrigen = obtenerPosicion(pieza, false);

                            if (pieza.mover(xOrigen, yOrigen, x, y, this)) {
                                casillas.get(x).set(y, pieza);
                                casillas.get(xOrigen).set(yOrigen, null);

                                if (!estaEnJaque(jugador)) {
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

    public boolean puedeHacerEnroque(Jugador jugador, boolean enroqueCorto) {
        Pieza rey = encontrarRey(jugador.getColor());
        if (rey == null || rey.haSidoMovido()) return false;

        int filaRey = (jugador.getColor() == Color.BLANCO) ? 0 : 7;
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
            if (simularMovimientoYVerificarJaque(rey, i, filaRey, jugador)) return false;
        }

        return true;
    }

    public boolean hacerEnroque(Jugador jugador, boolean enroqueCorto) {
        if (puedeHacerEnroque(jugador, enroqueCorto)) {
            Pieza rey = encontrarRey(jugador.getColor());
            int filaRey = (jugador.getColor() == Color.BLANCO) ? 0 : 7;
            int columnaRey = 4;
            int direccion = enroqueCorto ? 1 : -1;

            casillas.get(columnaRey + 2 * direccion).set(filaRey, rey);
            casillas.get(columnaRey).set(filaRey, null);

            int columnaTorre = enroqueCorto ? 7 : 0;
            Pieza torre = casillas.get(columnaTorre).get(filaRey);
            casillas.get(columnaRey + direccion).set(filaRey, torre);
            casillas.get(columnaTorre).set(filaRey, null);

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

    private boolean simularMovimientoYVerificarJaque(Pieza pieza, int xDestino, int yDestino, Jugador jugador) {
        int xOrigen = obtenerPosicion(pieza, true);
        int yOrigen = obtenerPosicion(pieza, false);
        Pieza piezaDestinoOriginal = casillas.get(xDestino).get(yDestino);

        casillas.get(xDestino).set(yDestino, pieza);
        casillas.get(xOrigen).set(yOrigen, null);

        boolean sigueEnJaque = estaEnJaque(jugador);

        casillas.get(xOrigen).set(yOrigen, pieza);
        casillas.get(xDestino).set(yDestino, piezaDestinoOriginal);

        return sigueEnJaque;
    }
}
