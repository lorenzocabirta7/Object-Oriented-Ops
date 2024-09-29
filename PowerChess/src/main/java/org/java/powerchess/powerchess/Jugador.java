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
        int xOrigen = tablero.obtenerPosicionX(pieza);
        int yOrigen = tablero.obtenerPosicionY(pieza);

        return tablero.moverPieza(pieza, xOrigen, yOrigen, xDestino, yDestino);
    }


    public boolean activarPoder(Poder poder, Pieza pieza) {
        if (poderes.contains(poder)) {
            poder.activar(pieza);
            poderes.remove(poder);
            return true;
        }
        return false;
    }

    public String getNombre() {
        return nombre;
    }

    public Color getColor() {
        return color;
    }

    public void agregarPoder(Poder poder) {
        poderes.add(poder);
    }
}
