package org.java.powerchess.powerchess;

import java.util.Observable;

public abstract class Pieza extends Observable{
    private Color color;
    private boolean fueMovida;
    private boolean congelada;
    private boolean escudada;
    protected String nombre;

    public Pieza(Color color) {
        this.color = color;
        this.fueMovida = false;
        this.congelada = false;
        this.escudada = false;
        this.nombre = "";
    }

    public boolean mover(int xOrigen, int yOrigen, int xDestino, int yDestino, Tablero tablero) {
        if (!congelada && this.verificarMovimiento(xOrigen, yOrigen, xDestino, yDestino, tablero)) {
            fueMovida = true;
            return true;
        }
        return false;
    }

    public boolean haSidoMovido(){ return fueMovida; }

    public abstract boolean puedeCoronar(int yActual);

    public Color getColor() {
        return color;
    }

    public boolean estaProtegida() {
        return escudada;
    }

    public boolean estaCongelada() {
      return congelada;
    }

    public void setCongelada(boolean congelada) {
        this.congelada = congelada;
        setChanged();
        notifyObservers();
    }

    public void setEscudada(boolean escudada) {
        this.escudada = escudada;
          
        setChanged();
        notifyObservers();
    }

    public String getNombre() {return this.nombre;}

    public abstract boolean verificarMovimiento(int xOrigen, int yOrigen, int xDestino, int yDestino, Tablero tablero);
}
