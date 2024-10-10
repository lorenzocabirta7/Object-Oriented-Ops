package org.java.powerchess.powerchess;

public abstract class PoderDeDuracion implements Poder {
    private final int CANTIDAD_DE_TURNOS = 3;
    private int turnos;
    protected boolean habilitado;
    protected boolean usado;
    protected Pieza piezaAfectada;

    public PoderDeDuracion() { 
      this.turnos = CANTIDAD_DE_TURNOS;
      habilitado = true;
    }

    public void disminuirTurnos() { if(turnos > 0){this.turnos--;} }

    public int getTurnos() { return this.turnos; }

    public boolean esUsado() { return this.usado; }

    public abstract void desactivar();
}
