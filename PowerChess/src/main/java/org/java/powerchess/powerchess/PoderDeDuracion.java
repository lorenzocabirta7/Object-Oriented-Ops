package org.java.powerchess.powerchess;

public abstract class PoderDeDuracion implements Poder {
    private static final int TURNOS_REQUERIDOS = 2;
    private int turnos;

    public PoderDeDuracion() { this.turnos = 0; }

    public PoderDeDuracion(int turnos) {
        this.turnos = turnos;
    }

    public void incrementarTurnos() { turnos += 1; }

    public boolean verificarTurnos() { return turnos == TURNOS_REQUERIDOS; }

    public int getTurnos() { return turnos; }
}
