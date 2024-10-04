package org.java.powerchess.powerchess.vista;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import org.java.powerchess.powerchess.Celda;
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
    private Celda celda;
    ImageView piezaImgView;

    private final HashMap<String, String> imgPieza = new HashMap<>() {{
        put("TorreB", "src/main/java/org/java/powerchess/powerchess/vista/imagenes/torreBlanca.png");
        put("AlfilB", "src/main/java/org/java/powerchess/powerchess/vista/imagenes/alfilBlanco.png");
        put("CaballoB", "src/main/java/org/java/powerchess/powerchess/vista/imagenes/caballoBlanco.png");
        put("ReinaB", "src/main/java/org/java/powerchess/powerchess/vista/imagenes/reinaBlanca.png");
        put("ReyB", "src/main/java/org/java/powerchess/powerchess/vista/imagenes/reyBlanco.png");
        put("PeonB", "src/main/java/org/java/powerchess/powerchess/vista/imagenes/peonBlanco.png");
        put("TorreN", "src/main/java/org/java/powerchess/powerchess/vista/imagenes/torreNegra.png");
        put("AlfilN", "src/main/java/org/java/powerchess/powerchess/vista/imagenes/alfilNegro.png");
        put("CaballoN", "src/main/java/org/java/powerchess/powerchess/vista/imagenes/caballoNegro.png");
        put("ReinaN", "src/main/java/org/java/powerchess/powerchess/vista/imagenes/reinaNegra.png");
        put("ReyN", "src/main/java/org/java/powerchess/powerchess/vista/imagenes/reyNegro.png");
        put("PeonN", "src/main/java/org/java/powerchess/powerchess/vista/imagenes/peonNegro.png");
    }};

    public VistaCasilla(Celda celda, double anchoCeldaGridPane, double alturaCeldaGridPane) {
        super();
        this.celda = celda;
        this.celda.addObserver(this);
        if ( ! celda.celdaVacia() ) {
            Pieza pieza = celda.obtenerPieza();
            String color = ( pieza.getColor() == Color.BLANCO ) ? "B":"N";
            try {
                InputStream piezaIS = new FileInputStream(imgPieza.get(pieza.getNombre() + color));
                Image piezaCasilla = new Image(piezaIS);
                piezaImgView = new ImageView(piezaCasilla);

                piezaImgView.setFitWidth(anchoCeldaGridPane);
                piezaImgView.setFitHeight(alturaCeldaGridPane);

                getChildren().add(piezaImgView);
            } catch (Exception e) {
            }

        }
        /*
        * Aca iria la llamada a MouseController / MouseControllerDragged
        * */

        ControladorMouse eventHandler = new ControladorMouse(celda);
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
    }

    @Override
    public void update(Observable observable, Object o) {
        if ( this.celda.estaSeleccionada() ) {
            this.setBackground(Background.fill(new javafx.scene.paint.Color(0,0,1,0.2)));
        }
        else {
            this.setBackground(null);
        }
    }
}
