package org.java.powerchess.powerchess;

public class Blanco implements Color {
    public static final Blanco BLANCO = new Blanco(); // Constante estática para comparación

    private Blanco() {}

    @Override
    public String getColor() {
        return "Blanco";
    }
}
