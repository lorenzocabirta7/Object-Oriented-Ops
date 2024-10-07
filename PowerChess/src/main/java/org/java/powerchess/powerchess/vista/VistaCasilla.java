package org.java.powerchess.powerchess.vista;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.util.Pair;
import org.java.powerchess.powerchess.Color;
import org.java.powerchess.powerchess.Juego;
import org.java.powerchess.powerchess.Pieza;
import org.java.powerchess.powerchess.Tablero;
import org.java.powerchess.powerchess.controlador.ControladorMouse;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class VistaCasilla extends StackPane implements Observer {
    private Juego juego;
    private Pair<Integer, Integer> casillaActual;
    ImageView piezaImgView;
    private double anchoCeldaGridPane;
    private double alturaCeldaGridPane;

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

    public VistaCasilla(Juego juego, Pair<Integer, Integer> casillaActual, double anchoCeldaGridPane, double alturaCeldaGridPane) {
        super();
        this.juego = juego;
        this.casillaActual = casillaActual;
        this.anchoCeldaGridPane = anchoCeldaGridPane;
        this.alturaCeldaGridPane = alturaCeldaGridPane;
        this.juego.addObserver(this);

        Tablero tablero = this.juego.obtenerTablero();
        tablero.addObserver(this);
        if ( ! tablero.casillaVacia(this.casillaActual.getKey(), this.casillaActual.getValue()) ) {
            Pieza pieza = tablero.obtenerPieza(this.casillaActual.getKey(), this.casillaActual.getValue());
            cargarImagenDePieza(pieza);
        }

        ControladorMouse eventHandler = new ControladorMouse(this.juego, this.casillaActual);
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
    }

    @Override
    public void update(Observable observable, Object o) {
        Tablero tablero = this.juego.obtenerTablero();
        Pieza pieza = tablero.obtenerPieza(casillaActual.getKey(), this.casillaActual.getValue());
        cargarImagenDePieza(tablero.obtenerPieza(casillaActual.getKey(), casillaActual.getValue()));
        if ( tablero.casillaEstaSeleccionada(this.casillaActual) ) {
            this.setBackground(Background.fill(new javafx.scene.paint.Color(0,0,1,0.2)));
        }
        else {
            this.setBackground(null);
        }
        // TODO: si hubo un movimiento, hay que mover la pieza (o sea, cambiar la imagen mostrada)
    }

    private void cargarImagenDePieza(Pieza pieza) {
        if ( pieza == null ) {
            //getChildren().remove(piezaImgView);
            if (piezaImgView != null){
                //piezaImgView.setImage(null);
                piezaImgView.setImage(null);
            }
            return;
        }
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
}
