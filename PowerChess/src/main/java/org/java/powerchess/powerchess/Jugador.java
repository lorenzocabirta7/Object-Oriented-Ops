package org.java.powerchess.powerchess;

import java.util.ArrayList;
import java.util.List;

public class Jugador {
    private String nombre;
    private Color color;
    private List<Poder> poderes;

    public Jugador(String nombre, Color color) {
        this.nombre = nombre;
        this.color = color;
        this.poderes = new ArrayList<>();
    }

    public boolean moverPieza(Pieza pieza, int xDestino, int yDestino, Tablero tablero) {
        if (pieza.getColor() != this.color) {
            System.out.println("No puedes mover una pieza del oponente.");
            return false;
        }

        if (tablero.moverPieza(pieza, xDestino, yDestino)) {
            return true;
        } else {
            System.out.println("Movimiento no válido.");
            return false;
        }
    }

    public boolean activarPoder(Poder poder, Pieza pieza) {
        if (poderes.contains(poder)) {
            poder.activar(pieza);
            poderes.remove(poder);
            return true;
        } else {
            System.out.println("No tienes este poder disponible.");
            return false;
        }
    }

    /*public void ofrecerTablas(Juego juego) {
        juego.ofrecerTablas(this);
    }

    public void responderTablas(Juego juego, boolean acepta) {
        juego.responderTablas(this, acepta);
    }

    public void rendirse(Juego juego) {
        juego.rendirse(this);
    }*/

    public String getNombre() {
        return nombre;
    }

    public Color getColor() {
        return color;
    }

    public void agregarPoder(Poder poder) {
        poderes.add(poder);
    }

    public void removerPoder(Poder poder) {
        poderes.remove(poder);
    }

}

