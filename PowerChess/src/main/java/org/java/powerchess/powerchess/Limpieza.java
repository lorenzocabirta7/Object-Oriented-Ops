package org.java.powerchess.powerchess;

public class Limpieza extends PoderDeAccion {
    public void activar(Pieza pieza) {
        pieza.setCongelada(false);
        pieza.setEscudada(false);
        this.habilitado = false;
    }

    public String getNombre(){
        return "Limpieza";
    }

    public boolean habilitado() {
      return this.habilitado;
    }
    
}
