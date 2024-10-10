package org.java.powerchess.powerchess;

public class Freeze extends PoderDeDuracion {
    @Override
    public void activar(Pieza pieza) {
        pieza.setCongelada(true);
        this.piezaAfectada = pieza;
        this.habilitado = false;
        this.usado = true;
    }

    @Override
    public void desactivar() {
      piezaAfectada.setCongelada(false);
      this.usado = false;
      this.piezaAfectada = null;
    }

    public String getNombre(){
        return "Freeze";
    }

    public boolean habilitado() {
      return this.habilitado;
    }
}
