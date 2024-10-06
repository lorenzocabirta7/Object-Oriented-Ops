package org.java.powerchess.powerchess.vista;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.util.Pair;
import org.java.powerchess.powerchess.Color;
import org.java.powerchess.powerchess.Pieza;
import org.java.powerchess.powerchess.Tablero;
import org.java.powerchess.powerchess.controlador.ControladorMouse;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class VistaCasilla extends StackPane implements Observer {
    private Tablero tablero;
    private Pair<Integer, Integer> casillaActual;
    ImageView piezaImgView;

    private final HashMap<String, String> imgPieza = new HashMap<>() {{
        put("TorreB", "PowerChess/src/main/java/org/java/powerchess/powerchess/vista/imagenes/torreBlanca.png");
        put("AlfilB", "PowerChess/src/main/java/org/java/powerchess/powerchess/vista/imagenes/alfilBlanco.png");
        put("CaballoB", "PowerChess/src/main/java/org/java/powerchess/powerchess/vista/imagenes/caballoBlanco.png");
        put("ReinaB", "PowerChess/src/main/java/org/java/powerchess/powerchess/vista/imagenes/reinaBlanca.png");
        put("ReyB", "PowerChess/src/main/java/org/java/powerchess/powerchess/vista/imagenes/reyBlanco.png");
        put("PeonB", "PowerChess/src/main/java/org/java/powerchess/powerchess/vista/imagenes/peonBlanco.png");
        put("TorreN", "PowerChess/src/main/java/org/java/powerchess/powerchess/vista/imagenes/torreNegra.png");
        put("AlfilN", "PowerChess/src/main/java/org/java/powerchess/powerchess/vista/imagenes/alfilNegro.png");
        put("CaballoN", "PowerChess/src/main/java/org/java/powerchess/powerchess/vista/imagenes/caballoNegro.png");
        put("ReinaN", "PowerChess/src/main/java/org/java/powerchess/powerchess/vista/imagenes/reinaNegra.png");
        put("ReyN", "PowerChess/src/main/java/org/java/powerchess/powerchess/vista/imagenes/reyNegro.png");
        put("PeonN", "PowerChess/src/main/java/org/java/powerchess/powerchess/vista/imagenes/peonNegro.png");
    }};

    public VistaCasilla(Tablero tablero, Pair<Integer, Integer> casillaActual, double anchoCeldaGridPane, double alturaCeldaGridPane) {
        super();
        this.tablero = tablero;
        this.casillaActual = casillaActual;
        this.tablero.addObserver(this);

        if ( ! tablero.casillaVacia(this.casillaActual.getKey(), this.casillaActual.getValue()) ) {
            Pieza pieza = tablero.obtenerPieza(this.casillaActual.getKey(), this.casillaActual.getValue());
            String color = ( pieza.getColor() == Color.BLANCO ) ? "B":"N";
            try {
                InputStream piezaIS = new FileInputStream(imgPieza.get(pieza.getNombre() + color));
                Image piezaCasilla = new Image(piezaIS);
                piezaImgView = new ImageView(piezaCasilla);

                piezaImgView.setFitWidth(anchoCeldaGridPane);
                piezaImgView.setFitHeight(alturaCeldaGridPane);

                getChildren().add(piezaImgView);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }

        ControladorMouse eventHandler = new ControladorMouse(this.tablero, this.casillaActual);
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
    }

    @Override
    public void update(Observable observable, Object o) {
        if ( this.tablero.casillaEstaSeleccionada(this.casillaActual) ) {
            this.setBackground(Background.fill(new javafx.scene.paint.Color(0,0,1,0.2)));
        }
        else {
            this.setBackground(null);
        }
    }
}
