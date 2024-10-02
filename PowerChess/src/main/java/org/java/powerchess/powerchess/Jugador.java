package org.java.powerchess.powerchess;

import java.util.ArrayList;
import java.util.List;

public class Jugador {
    private String nombre;
    private Color color;
    private List<Poder> poderes;

    public Jugador(String nombre, Color color) {
        this.nombre = nombre;
        this.color = color;
        this.poderes = new ArrayList<>();
        //Hardcodear poderes
    }

    public boolean moverPieza(Pieza pieza, int xDestino, int yDestino, Tablero tablero) {
        int xOrigen = tablero.obtenerPosicion(pieza, true);
        int yOrigen = tablero.obtenerPosicion(pieza, false);

        return tablero.moverPieza(pieza, xOrigen, yOrigen, xDestino, yDestino);
    }


    public void activarPoder(Poder poder, Pieza pieza) {
        if (poderes.contains(poder)) {
            poder.activar(pieza);
            poderes.remove(poder);
        }
    }

    public void desactivarPoder(PoderDeDuracion poder, Pieza pieza) {
        poder.desactivar(pieza);
    }

    public String getNombre() {
        return nombre;
    }

    public Color getColor() {
        return color;
    }

    public List<Poder> getPoderes() { return poderes;}

    public void agregarPoder(Poder poder) {
        poderes.add(poder);
    }

    public void removerPoder(Poder poder) {
        if (poderes.contains(poder)) {
            poderes.remove(poder);
        }
    }

    public void actualizarPoderesDeDuracion(){
        for (Poder poder : poderes) {
            if (poder.equals(new Freeze()) || poder.equals(new Escudo())){
                PoderDeDuracion poderDeDuracion = (PoderDeDuracion) poder;
                poderDeDuracion.disminuirTurnos();
                if (poderDeDuracion.getTurnos() == 0) {
                    poderes.remove(poder);
                }
            }
        }
    }
}
