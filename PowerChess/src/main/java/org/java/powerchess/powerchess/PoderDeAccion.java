package org.java.powerchess.powerchess;

public abstract class PoderDeAccion implements Poder {
    protected boolean habilitado;

    public PoderDeAccion() {
      this.habilitado = true;
    }


    @Override
    public abstract void activar(Pieza pieza);
}
