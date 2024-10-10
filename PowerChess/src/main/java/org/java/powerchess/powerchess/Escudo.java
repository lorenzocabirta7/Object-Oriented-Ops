package org.java.powerchess.powerchess;

public class Escudo extends PoderDeDuracion {
    @Override
    public void activar(Pieza pieza) {
        pieza.setEscudada(true);
        this.piezaAfectada = pieza;
        this.habilitado = false;
        this.usado = true;
    }

    @Override
    public void desactivar() {
      piezaAfectada.setEscudada(false);
      this.usado = false;
      this.piezaAfectada = null;
    }

    public String getNombre(){
      return "Escudo";
    }

    public boolean habilitado() {
      return this.habilitado;
    }
}
