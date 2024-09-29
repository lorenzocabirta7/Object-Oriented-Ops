package org.java.powerchess.powerchess;

public abstract class PoderDeDuracion implements Poder {
    private int turnos;

    public PoderDeDuracion(int turnos) {
        this.turnos = turnos;
    }

    public int getTurnos() {
        return turnos;
    }
}
