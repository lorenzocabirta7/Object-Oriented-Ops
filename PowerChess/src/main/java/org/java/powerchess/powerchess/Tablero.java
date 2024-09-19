package org.java.powerchess.powerchess;

public class Tablero {
    private Pieza[][] casillas;
    private final int tamanio = 8; // Tamaño del tablero de ajedrez estándar

    public Tablero() {
        casillas = new Pieza[tamanio][tamanio];
        inicializarPiezas();
    }

    private void inicializarPiezas() {
        //Peones
        for (int i = 0; i < tamanio; i++) {
            casillas[i][1] = new Pieza(i, 1, new Peon(), new Blanco());
            casillas[i][6] = new Pieza(i, 6, new Peon(), new Negro());
        }

        //Resto de piezas
        inicializarPiezasEspeciales(0, new Blanco());
        inicializarPiezasEspeciales(7, new Negro());
    }

    private void inicializarPiezasEspeciales(int fila, Color color) {
        casillas[0][fila] = new Pieza(0, fila, new Torre(), color);
        casillas[7][fila] = new Pieza(7, fila, new Torre(), color);
        casillas[1][fila] = new Pieza(1, fila, new Caballo(), color);
        casillas[6][fila] = new Pieza(6, fila, new Caballo(), color);
        casillas[2][fila] = new Pieza(2, fila, new Alfil(), color);
        casillas[5][fila] = new Pieza(5, fila, new Alfil(), color);
        casillas[3][fila] = new Pieza(3, fila, new Reina(), color);
        casillas[4][fila] = new Pieza(4, fila, new Rey(), color);
    }

    public boolean moverPieza(Pieza pieza, int xDestino, int yDestino) {
        int xOrigen = pieza.getX();
        int yOrigen = pieza.getY();

        if (pieza.mover(xDestino, yDestino, this)) {
            //Actualizar tablero
            casillas[xOrigen][yOrigen] = null;
            casillas[xDestino][yDestino] = pieza;
            return true;
        }
        return false;
    }

    public boolean casillaVacia(int x, int y) {
        if (posicionDentroDelTablero(x, y)) {
            return casillas[x][y] == null;
        }
        return false;
    }

    public boolean hayPiezaEnemiga(int x, int y, Color color) {
        if (posicionDentroDelTablero(x, y)) {
            Pieza pieza = casillas[x][y];
            return pieza != null && pieza.getColor() != color;
        }
        return false;
    }

    public boolean hayObstaculosEntre(int xOrigen, int yOrigen, int xDestino, int yDestino) {
        int deltaX = Integer.signum(xDestino - xOrigen);
        int deltaY = Integer.signum(yDestino - yOrigen);

        int x = xOrigen + deltaX;
        int y = yOrigen + deltaY;

        while (x != xDestino || y != yDestino) {
            if (!casillaVacia(x, y)) {
                return true;
            }
            x += deltaX;
            y += deltaY;
        }
        return false;
    }

    public Pieza obtenerPieza(int x, int y) {
        if (posicionDentroDelTablero(x, y)) {
            return casillas[x][y];
        }
        return null;
    }

    private boolean posicionDentroDelTablero(int x, int y) {
        return x >= 0 && x < tamanio && y >= 0 && y < tamanio;
    }
}

