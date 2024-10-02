package org.java.powerchess.powerchess;

public class Escudo extends PoderDeDuracion {
    @Override
    public void activar(Pieza pieza) {
        pieza.setEscudada(true);
    }

    @Override
    public void desactivar(Pieza pieza) {
        pieza.setEscudada(false);
    }
}
