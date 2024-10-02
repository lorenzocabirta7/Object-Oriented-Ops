package org.java.powerchess.powerchess;

public class Freeze extends PoderDeDuracion {
    @Override
    public void activar(Pieza pieza) {
        pieza.setCongelada(true);
    }

    @Override
    public void desactivar(Pieza pieza) {
        pieza.setCongelada(false);
    }
}
