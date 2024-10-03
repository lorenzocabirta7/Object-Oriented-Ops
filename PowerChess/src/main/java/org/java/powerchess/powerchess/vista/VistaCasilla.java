package org.java.powerchess.powerchess.vista;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import org.java.powerchess.powerchess.Color;
import org.java.powerchess.powerchess.Pieza;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;

public class VistaCasilla extends StackPane {
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

    public VistaCasilla(Pieza pieza, double anchoCeldaGridPane, double alturaCeldaGridPane) {
        if ( pieza != null ) {
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
        // ControladorMouse eventHandler = new ControladorMouse(parcela);
        // this.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
        // new ControladorMouseDragged(this, juego, juegoController, parcela);
    }
}
