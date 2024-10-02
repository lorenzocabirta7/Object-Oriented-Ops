package org.java.powerchess.powerchess;

public abstract class PoderDeDuracion implements Poder {
    private final int CANTIDAD_DE_TURNOS = 3;
    private int turnos;

    public PoderDeDuracion() { this.turnos = CANTIDAD_DE_TURNOS; }

    public void disminuirTurnos() { if(turnos > 0){this.turnos--;} }

    public int getTurnos() { return this.turnos; }

    public abstract void desactivar(Pieza pieza);
}
