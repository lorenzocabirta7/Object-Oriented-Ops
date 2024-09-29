package org.java.powerchess.powerchess;

public class Freeze extends PoderDeDuracion {
    public Freeze(int turnos) {
        super(turnos);
    }

    @Override
    public void activar(Pieza pieza) {
        pieza.setCongelada(true);
    }
}
