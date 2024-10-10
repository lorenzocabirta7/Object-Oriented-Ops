package org.java.powerchess.powerchess;

import java.util.Observable;

public class Juego extends Observable {
    private Jugador jugadorBlanco;
    private Jugador jugadorNegro;
    private Tablero tablero;
    private Jugador turnoActual;
    private boolean enJuego;
    private Jugador jugadorGanador;
    private boolean tablasOfrecidas;
    private boolean hayCoronacion = false;

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
                if (tablero.puedeCoronar(xDestino, yDestino)) {
                    hayCoronacion = true;
                    setChanged();
                    notifyObservers(hayCoronacion);
                }
                else {
                    cambiarTurno();
                }
                return true;
            }
        }
        return false;
    }

    public void ofrecerTablas() {
        tablasOfrecidas = true;
    }

    public void responderTablas(boolean acepta) {
        if (tablasOfrecidas) {
            if (acepta) {
                terminarPartida(null);
            } else {
                tablasOfrecidas = false;
            }
        }
    }

    public void rendirse(){
        Jugador ganador = (turnoActual == jugadorBlanco) ? jugadorNegro : jugadorBlanco;
        terminarPartida(ganador);
    }

    public void terminarPartida(Jugador ganador) {
        enJuego = false;
        jugadorGanador = ganador;

        setChanged();
        notifyObservers();
    }
    
    public boolean partidaTerminada() {
      return !enJuego;
    }

    public Jugador getJugadorGanador() {
      return jugadorGanador;
    }

    public void cambiarTurno() {
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
