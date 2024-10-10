package org.java.powerchess.powerchess;

public interface Poder {
    void activar(Pieza pieza);
    boolean habilitado();
    String getNombre();
}
