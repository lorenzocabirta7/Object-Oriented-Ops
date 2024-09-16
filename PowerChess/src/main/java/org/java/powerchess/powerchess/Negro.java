package org.java.powerchess.powerchess;

public class Negro implements Color {
    public static final Negro NEGRO = new Negro(); // Constante estática para comparación

    private Negro() {}
    @Override
    public String getColor() {
        return "Negro";
    }
}
