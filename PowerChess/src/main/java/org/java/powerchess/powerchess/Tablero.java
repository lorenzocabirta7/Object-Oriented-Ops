package org.java.powerchess.powerchess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Tablero {
    private List<List<Celda>> casillas;
    private final int tamanio = 8;

    public Tablero() {
        casillas = new ArrayList<>();
        for (int i = 0; i < tamanio; i++) {
            List<Celda> fila = new ArrayList<>();
            for (int j = 0; j < tamanio; j++) {
                fila.add( new Celda(null, i, j) );
            }
            casillas.add(fila);
        }
        inicializarPiezas();
    }

    private void inicializarPiezas() {
        for (int i = 0; i < tamanio; i++) {
            casillas.get(i).get(1).recibirPieza(new Pieza(Color.BLANCO, new Peon()));
            casillas.get(i).get(6).recibirPieza(new Pieza(Color.NEGRO, new Peon()));
        }
        inicializarPiezasEspeciales(0, Color.BLANCO);
        inicializarPiezasEspeciales(7, Color.NEGRO);
    }

    private void inicializarPiezasEspeciales(int fila, Color color) {
        casillas.get(0).get(fila).recibirPieza(new Pieza(color, new Torre()));
        casillas.get(7).get(fila).recibirPieza(new Pieza(color, new Torre()));
        casillas.get(1).get(fila).recibirPieza(new Pieza(color, new Caballo()));
        casillas.get(6).get(fila).recibirPieza(new Pieza(color, new Caballo()));
        casillas.get(2).get(fila).recibirPieza(new Pieza(color, new Alfil()));
        casillas.get(5).get(fila).recibirPieza(new Pieza(color, new Alfil()));
        casillas.get(3).get(fila).recibirPieza(new Pieza(color, new Reina(new Torre(), new Alfil())));
        casillas.get(4).get(fila).recibirPieza(new Pieza(color, new Rey()));

    }

    private boolean piezaEnemigaEscudada(int xDestino, int yDestino){
        Pieza piezaEnemiga = obtenerPieza(xDestino, yDestino);
        return piezaEnemiga.estaProtegida();
    }

    public boolean moverPieza(Pieza pieza, int xOrigen, int yOrigen, int xDestino, int yDestino) {
        if (pieza.mover(xOrigen, yOrigen, xDestino, yDestino, this) && !piezaEnemigaEscudada(xDestino, yDestino)) {
            casillas.get(xDestino).get(yDestino).recibirPieza(pieza);
            casillas.get(xOrigen).get(yOrigen).recibirPieza(null);
            return true;
        }
        return false;
    }

    public boolean casillaVacia(int x, int y) {
        return posicionDentroDelTablero(x, y) && casillas.get(x).get(y).celdaVacia();
    }

    public boolean casillaEstaSeleccionada(int x, int y) {
        return casillas.get(x).get(y).estaSeleccionada();
    }

    // Selecciona la casilla si no estaba seleccionada. Si estaba seleccionada, la des-selecciona.
    public void cambiarSeleccionCasilla(int x, int y) {
        this.casillas.get(x).get(y).cambiarSeleccion();
    }

    public boolean hayPiezaEnemiga(int x, int y, Color color) {
        if (posicionDentroDelTablero(x, y)) {
            return casillas.get(x).get(y).contienePiezaEnemiga(color);
        }
        return false;
    }

    public boolean posicionDentroDelTablero(int x, int y) {
        return x >= 0 && x < tamanio && y >= 0 && y < tamanio;
    }

    public Pieza obtenerPieza(int x, int y) {
        if (posicionDentroDelTablero(x, y)) {
            return casillas.get(x).get(y).obtenerPieza();
        }
        return null;
    }

    public Celda obtenerCelda(int x, int y) {
        return this.casillas.get(x).get(y);
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
        for (List<Celda> fila : casillas) {
            for (Celda celda : fila) {
                Pieza pieza = celda.obtenerPieza();
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

        for (List<Celda> fila : casillas) {
            for (Celda celda : fila) {
                Pieza pieza = celda.obtenerPieza();
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

        for (List<Celda> fila : casillas) {
            for (Celda celda : fila) {
                Pieza pieza = celda.obtenerPieza();
                if (pieza != null && pieza.getColor() == jugador.getColor()) {
                    for (int x = 0; x < tamanio; x++) {
                        for (int y = 0; y < tamanio; y++) {
                            Pieza piezaDestino = casillas.get(x).get(y).obtenerPieza();
                            int xOrigen = obtenerPosicion(pieza, true);
                            int yOrigen = obtenerPosicion(pieza, false);

                            if (pieza.mover(xOrigen, yOrigen, x, y, this)) {
                                casillas.get(x).get(y).recibirPieza(pieza);
                                casillas.get(xOrigen).set(yOrigen, null);

                                if (!estaEnJaque(jugador)) {
                                    casillas.get(xOrigen).get(yOrigen).recibirPieza(pieza);
                                    casillas.get(x).get(y).recibirPieza(piezaDestino);
                                    return false;
                                }

                                casillas.get(xOrigen).get(yOrigen).recibirPieza(pieza);
                                casillas.get(x).get(y).recibirPieza(piezaDestino);
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

        Pieza torre = casillas.get(columnaTorre).get(filaRey).obtenerPieza();

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

            casillas.get(columnaRey + 2 * direccion).get(filaRey).recibirPieza(rey);
            casillas.get(columnaRey).set(filaRey, null);

            int columnaTorre = enroqueCorto ? 7 : 0;
            Pieza torre = casillas.get(columnaTorre).get(filaRey).obtenerPieza();
            casillas.get(columnaRey + direccion).get(filaRey).recibirPieza(torre);
            casillas.get(columnaTorre).set(filaRey, null);

            return true;
        }
        return false;
    }

    public int obtenerPosicion(Pieza pieza, boolean obtenerX) {
        for (int x = 0; x < tamanio; x++) {
            for (int y = 0; y < tamanio; y++) {
                if (casillas.get(x).get(y).obtenerPieza() == pieza) {
                    return obtenerX ? x : y;
                }
            }
        }
        return -1;
    }

    private boolean simularMovimientoYVerificarJaque(Pieza pieza, int xDestino, int yDestino, Jugador jugador) {
        int xOrigen = obtenerPosicion(pieza, true);
        int yOrigen = obtenerPosicion(pieza, false);
        Pieza piezaDestinoOriginal = casillas.get(xDestino).get(yDestino).obtenerPieza();

        casillas.get(xDestino).get(yDestino).recibirPieza(pieza);
        casillas.get(xOrigen).set(yOrigen, null);

        boolean sigueEnJaque = estaEnJaque(jugador);

        casillas.get(xOrigen).get(yOrigen).recibirPieza(pieza);
        casillas.get(xDestino).get(yDestino).recibirPieza(piezaDestinoOriginal);

        return sigueEnJaque;
    }
}
