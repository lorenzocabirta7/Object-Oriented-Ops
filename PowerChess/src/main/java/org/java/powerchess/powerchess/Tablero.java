package org.java.powerchess.powerchess;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Optional;

public class Tablero extends Observable {
    private List<List<Pieza>> casillas;
    private final int tamanio = 8;
    private Pair<Integer, Integer> casillaSeleccionada = new Pair<>(-1,-1); // cuando las coordenadas son (-1, -1) no esta seleccionada

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
            casillas.get(i).set(1, new Peon(Color.BLANCO));
            casillas.get(i).set(6, new Peon(Color.NEGRO));
        }
        inicializarPiezasEspeciales(0, Color.BLANCO);
        inicializarPiezasEspeciales(7, Color.NEGRO);
    }

    private void inicializarPiezasEspeciales(int fila, Color color) {
        casillas.get(0).set(fila, new Torre(color));
        casillas.get(7).set(fila, new Torre(color));
        casillas.get(1).set(fila, new Caballo(color));
        casillas.get(6).set(fila, new Caballo(color));
        casillas.get(2).set(fila, new Alfil(color));
        casillas.get(5).set(fila, new Alfil(color));
        casillas.get(4).set(fila, new Reina(color));
        casillas.get(3).set(fila, new Rey(color));
    }

    private boolean piezaEnemigaEscudada(int xDestino, int yDestino){
        Pieza piezaEnemiga = obtenerPieza(xDestino, yDestino);
        return piezaEnemiga.estaProtegida();
    }

    public boolean moverPieza(int xOrigen, int yOrigen, int xDestino, int yDestino, Color color) {
        try {
            Pieza pieza;
            if ((pieza = this.obtenerPieza(xOrigen, yOrigen)) == null) {
                return false;
            }
            if (pieza.getColor() != color) {
                return false;
            }

            Pieza piezaDestino = obtenerPieza(xDestino, yDestino);

            if (!hayPiezaEnemiga(xDestino, yDestino, color) && piezaDestino != null) {
                Enroque enroque = new Enroque();
                Pair<Integer, Integer> posicionesFinales = enroque.hacerEnroque(pieza, piezaDestino, this);
                if ( posicionesFinales != null ) {
                    /* Al enrocar, no varia la posicion del eje y */

                    casillas.get(posicionesFinales.getKey()).set(yOrigen, pieza);
                    casillas.get(xOrigen).set(yOrigen, null);

                    casillas.get(posicionesFinales.getValue()).set(yOrigen, piezaDestino);
                    casillas.get(xDestino).set(yOrigen, null);

                    setChanged();
                    return true;
                }

            }
            if (pieza.mover(xOrigen, yOrigen, xDestino, yDestino, this)) {
                if (hayPiezaEnemiga(xDestino, yDestino, color) && piezaEnemigaEscudada(xDestino, yDestino)) {
                    return false;
                }

                if (!simularMovimientoYVerificarQueNoEstaEnJaque(pieza, xDestino, yDestino, color, null)) {
                    return false;
                }

                casillas.get(xDestino).set(yDestino, pieza);
                casillas.get(xOrigen).set(yOrigen, null);
                setChanged();
                return true;
            }

            throw new MovimientoInvalidoException();
        } catch (MovimientoInvalidoException e) {
            return false;
        }
    }
    public boolean casillaVacia(int x, int y) {
        return posicionDentroDelTablero(x, y) && casillas.get(x).get(y) == null;
    }

    public boolean casillaEstaSeleccionada(Pair<Integer, Integer> coordenadas) {
        return coordenadas.equals(this.casillaSeleccionada);
    }

    // Si la casilla estaba seleccionada, la deselecciona. Sino, la selecciona.
    public void cambiarSeleccionCasilla(Pair<Integer, Integer> coordenadas) {
        this.casillaSeleccionada = ( coordenadas.equals(this.casillaSeleccionada) ) ? new Pair<>(-1, -1) : coordenadas;
        setChanged();
        notifyObservers();
    }

    public boolean hayUnaCasillaSeleccionada() {
        return ! this.casillaSeleccionada.equals(new Pair<>(-1, -1));
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
                if (pieza != null && pieza.equals(new Rey(color))) {
                    return pieza;
                }
            }
        }
        return null;
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

    // False si esta en jaque o no se puede mover, True en caso contrario
    // Pone una pieza temporalmente en la casilla destino y verifica que no quede en jaque
    // Se le puede pasar el Rey, o null y que el metodo se encargue de encuentrar al Rey.
    public boolean simularMovimientoYVerificarQueNoEstaEnJaque(Pieza pieza, int xDestino, int yDestino, Color colorDelJugador, Rey reyDelJugador) {
        Optional<Rey> rey = Optional.ofNullable(reyDelJugador);

        int xOrigen = obtenerPosicion(pieza, true);
        int yOrigen = obtenerPosicion(pieza, false);

        Pieza piezaDestinoOriginal = casillas.get(xDestino).get(yDestino);

        casillas.get(xDestino).set(yDestino, pieza);
        casillas.get(xOrigen).set(yOrigen, null);

        Rey piezaRey = rey.orElseGet(() -> (Rey) encontrarRey(colorDelJugador));
        boolean noEstaEnJaque = ! piezaRey.verSiEstaEnJaque(this);

        casillas.get(xOrigen).set(yOrigen, pieza);
        casillas.get(xDestino).set(yDestino, piezaDestinoOriginal);

        return noEstaEnJaque;
    }

    public boolean puedeCoronar(int x, int y) {
        Pieza pieza = casillas.get(x).get(y);
        return  pieza != null && pieza.puedeCoronar(y);
    }

    public void coronar(int x, int y, Pieza nuevaPieza) {
        this.casillas.get(x).set(y, nuevaPieza);
        setChanged();
    }

    public List<Pieza> obtenerPiezasEnemigas(Color colorDelJugador) {
        List<Pieza> piezasEnemigas = new ArrayList<>();

        for (List<Pieza> fila : casillas) {
            for (Pieza pieza : fila) {
                if (pieza != null && pieza.getColor() != colorDelJugador) {
                    piezasEnemigas.add(pieza);
                }
            }
        }

        return piezasEnemigas;
    }

    public List<Pieza> obtenerPiezasDelJugador(Color colorDelJugador) {
        List<Pieza> piezasDelJugador = new ArrayList<>();
        for (List<Pieza> fila : casillas) {
            for (Pieza pieza : fila) {
                if (pieza != null && pieza.getColor() == colorDelJugador){
                    piezasDelJugador.add(pieza);
                }
            }
        }
        return piezasDelJugador;
    }

    public int obtenerTamanioDelTablero() {return this.tamanio;}

    public boolean esJaqueMate(Color colorDelOponente) {
        Rey reyEnemigo = (Rey) encontrarRey(colorDelOponente);
        return reyEnemigo != null && reyEnemigo.verSiHayJaqueMate(this);
    }
}
