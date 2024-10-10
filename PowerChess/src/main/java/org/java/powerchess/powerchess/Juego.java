package org.java.powerchess.powerchess;

import java.util.Observable;

public class Juego extends Observable {
    private Jugador jugadorBlanco;
    private Jugador jugadorNegro;
    private Tablero tablero;
    private Jugador turnoActual;
    private boolean enJuego;
    private boolean tablasOfrecidas;

    public Juego(Jugador jugador1, Jugador jugador2) {
        if (jugador1.getColor() == Color.BLANCO) {
            this.jugadorBlanco = jugador1;
            this.jugadorNegro = jugador2;
        } else {
            this.jugadorBlanco = jugador2;
            this.jugadorNegro = jugador1;
        }

        this.tablero = new Tablero();
        this.turnoActual = jugadorBlanco;  // Siempre empieza el jugador con piezas blancas
        this.enJuego = true;
        this.tablasOfrecidas = false;
    }

    public boolean moverPieza(int xOrigen, int yOrigen, int xDestino, int yDestino) {
        if (enJuego) {
            if (turnoActual.moverPieza(xOrigen, yOrigen, xDestino, yDestino, this.tablero) ) {
                cambiarTurno();
                return true;
            }
        }
        return false;
    }

    public void ofrecerTablas() {
        tablasOfrecidas = true;
        System.out.println(turnoActual.getNombre() + " ha ofrecido tablas.");
    }

    public void responderTablas(boolean acepta) {
        if (tablasOfrecidas) {
            if (acepta) {
                System.out.println("Ambos jugadores han acordado tablas.");
                terminarPartida(null);
            } else {
                System.out.println(turnoActual.getNombre() + " ha rechazado las tablas.");
                tablasOfrecidas = false;
            }
        }
    }

    public void rendirse() {
        System.out.println(turnoActual.getNombre() + " se ha rendido.");
        Jugador ganador = (turnoActual == jugadorBlanco) ? jugadorNegro : jugadorBlanco;
        terminarPartida(ganador);
    }

    public void terminarPartida(Jugador ganador) {
        enJuego = false;
        if (ganador == null) {
            System.out.println("La partida ha terminado en tablas.");
        } else {
            System.out.println("El ganador es: " + ganador.getNombre());
        }
    }

    private void cambiarTurno() {
        Jugador oponente = (turnoActual == jugadorBlanco) ? jugadorNegro : jugadorBlanco;

        if (tablero.esJaqueMate(oponente.getColor())) {
            terminarPartida(turnoActual);
            return;
        }
        turnoActual = oponente;
        tablasOfrecidas = false;
        jugadorBlanco.actualizarPoderesDeDuracion();
        jugadorNegro.actualizarPoderesDeDuracion();
        setChanged();
        notifyObservers();
    }


    public Tablero obtenerTablero() { return this.tablero; }

    public Jugador obtenerJugadorActual() { return this.turnoActual; }

    public Color obtenerColorDelJugadorActual() { return this.turnoActual.getColor(); }
}
