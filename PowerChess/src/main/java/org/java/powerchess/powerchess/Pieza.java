package org.java.powerchess.powerchess;

public class Pieza {
    private int x;
    private int y;
    private EstadoPieza estadoPieza; // Estado actual de la pieza
    private Color color;
    //private Poder poderAplicado;

    public Pieza(int x, int y, EstadoPieza estadoPieza, Color color) {
        this.x = x;
        this.y = y;
        this.estadoPieza = estadoPieza; // Estado inicial
        this.color = color;
    }

    public boolean mover(int xDestino, int yDestino, Tablero tablero) {
        if (estadoPieza.verificarMovimiento(x, y, xDestino, yDestino, tablero)) {
            this.x = xDestino;
            this.y = yDestino;
            return true;
        }
        return false;
    }

    public void cambiarEstado(EstadoPieza nuevoEstado) {
        this.estadoPieza = nuevoEstado;
    }

    /*public void aplicarPoder(Poder poder) {
        this.poderAplicado = poder;
        poder.activar(this);
    }*/

    public Color getColor() {
        return color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
