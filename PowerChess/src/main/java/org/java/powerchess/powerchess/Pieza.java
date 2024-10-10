package org.java.powerchess.powerchess;

import java.util.Observable;
import java.security.PublicKey;

public class Pieza  extends Observable{
    private Color color;
    private EstadoPieza estadoPieza;
    private boolean fueMovida;
    private boolean congelada;
    private boolean escudada;

    public Pieza(Color color, EstadoPieza estadoPieza) {
        this.color = color;
        this.estadoPieza = estadoPieza;
        this.fueMovida = false;
        this.congelada = false;
        this.escudada = false;
    }

    public boolean mover(int xOrigen, int yOrigen, int xDestino, int yDestino, Tablero tablero) {
        if (!congelada && estadoPieza.verificarMovimiento(xOrigen, yOrigen, xDestino, yDestino, tablero)) {
            fueMovida = true;
            return true;
        }
        return false;
    }

    public boolean haSidoMovido(){ return fueMovida; }

    public boolean puedeCoronar(int yActual){
        return (yActual == 0 || yActual == 7) && esPeon();
    }

    public void coronar(EstadoPieza nuevoEstado){
        estadoPieza = nuevoEstado;


    }

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

    public boolean esRey() {
        return estadoPieza.esRey();
    }

    public boolean esTorre() {
        return estadoPieza.esTorre();
    }

    public boolean esPeon() {
        return estadoPieza.esPeon();
    }

    public String getNombre() {return this.estadoPieza.getNombre();}
}
