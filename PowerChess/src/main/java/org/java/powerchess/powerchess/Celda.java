package org.java.powerchess.powerchess;

import java.util.Observable;

public class Celda extends Observable {
    private Pieza pieza;
    private Boolean seleccionada = false;
    private int x;
    private int y;

    public Celda(Pieza pieza, int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void recibirPieza(Pieza pieza) {
        // Si no habia una pieza, o si quiero vaciar la casilla
        this.pieza = pieza;
        setChanged();
    }

    public void cambiarSeleccion() {
        this.seleccionada = !this.seleccionada;
        setChanged();
        notifyObservers();
    }

    public Boolean estaSeleccionada() {
        return this.seleccionada;
    }

    public Pieza obtenerPieza() {
        return pieza;
    }

    public Boolean celdaVacia() {
        return (this.pieza == null);
    }

    public Boolean contienePiezaEnemiga(Color color) {
        return this.pieza != null && this.pieza.getColor() != color;
    }
}
