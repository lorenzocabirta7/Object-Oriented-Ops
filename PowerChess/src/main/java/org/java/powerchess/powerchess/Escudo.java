package org.java.powerchess.powerchess;

public class Escudo extends PoderDeDuracion {
    public Escudo(int turnos) {
        super(turnos);
    }

    @Override
    public void activar(Pieza pieza) {
        pieza.setEscudada(true);
    }
}
