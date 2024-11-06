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
    /*TODO: borrar este constructor*/
    /*public Pieza(Color color, EstadoPieza estadoPieza) {
        this.color = color;
        //this.estadoPieza = estadoPieza;
        this.fueMovida = false;
        this.congelada = false;
        this.escudada = false;
    }*/

    public boolean mover(int xOrigen, int yOrigen, int xDestino, int yDestino, Tablero tablero) {
        if (!congelada && this.verificarMovimiento(xOrigen, yOrigen, xDestino, yDestino, tablero)) {
            fueMovida = true;
            return true;
        }
        return false;
    }

    public boolean haSidoMovido(){ return fueMovida; }

    public boolean puedeCoronar(int yActual){
        /* TODO: Moverlo a peon, o ver como hacerlo */
        return (yActual == 0 || yActual == 7) && esPeon();
    }

    /*public void coronar(EstadoPieza nuevoEstado){
        estadoPieza = nuevoEstado;
    }*/

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

    /* TODO: borrar estos 3 metodos */
    public boolean esRey() {
        //return estadoPieza.esRey();
        return false;
    }

    public boolean esTorre() {
        //return estadoPieza.esTorre();
        return false;
    }

    public boolean esPeon() {
        //return estadoPieza.esPeon();
        return false;
    }

    public String getNombre() {return this.nombre;}

    public abstract boolean verificarMovimiento(int xOrigen, int yOrigen, int xDestino, int yDestino, Tablero tablero);
}
