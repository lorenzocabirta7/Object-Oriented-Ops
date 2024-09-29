package org.java.powerchess.powerchess;

public class Pieza {
    private Color color;
    private EstadoPieza estadoPieza;
    private boolean congelada;
    private boolean escudada;

    public Pieza(Color color, EstadoPieza estadoPieza) {
        this.color = color;
        this.estadoPieza = estadoPieza;
    }

    public boolean mover(int xOrigen, int yOrigen, int xDestino, int yDestino, Tablero tablero) {
        if (!congelada && estadoPieza.verificarMovimiento(xOrigen, yOrigen, xDestino, yDestino, tablero)) {
            return true;
        }
        return false;
    }

    public Color getColor() {
        return color;
    }

    public boolean estaProtegida() {
        return escudada;
    }

    public void setCongelada(boolean congelada) {
        this.congelada = congelada;
    }

    public void setEscudada(boolean escudada) {
        this.escudada = escudada;
    }

    public boolean esRey() {
        return estadoPieza.esRey();
    }

    public boolean esTorre() {
        return estadoPieza.esTorre();
    }
}
