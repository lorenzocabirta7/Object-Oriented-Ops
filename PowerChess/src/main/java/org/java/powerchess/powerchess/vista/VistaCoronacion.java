package org.java.powerchess.powerchess.vista;

import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;
import org.java.powerchess.powerchess.Juego;
import org.java.powerchess.powerchess.controlador.ControladorMouseCasilla;
import org.java.powerchess.powerchess.controlador.ControladorMouseCoronacion;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

public class VistaCoronacion extends VBox implements Observer {
    GridPane gridPiezas;
    private List<ImageView> imageViews;
    private boolean panelVisible = false;

    private final HashMap<String, String> imgPiezasBlancas = new HashMap<>() {{
        put("Torre", "PowerChess/src/main/java/org/java/powerchess/powerchess/vista/imagenes/torreBlanca.png");
        put("Alfil", "PowerChess/src/main/java/org/java/powerchess/powerchess/vista/imagenes/alfilBlanco.png");
        put("Caballo", "PowerChess/src/main/java/org/java/powerchess/powerchess/vista/imagenes/caballoBlanco.png");
        put("Reina", "PowerChess/src/main/java/org/java/powerchess/powerchess/vista/imagenes/reinaBlanca.png");
    }};

    private final HashMap<String, String> imgPiezasNegras = new HashMap<>() {{
        put("Torre", "PowerChess/src/main/java/org/java/powerchess/powerchess/vista/imagenes/torreNegra.png");
        put("Alfil", "PowerChess/src/main/java/org/java/powerchess/powerchess/vista/imagenes/alfilNegro.png");
        put("Caballo", "PowerChess/src/main/java/org/java/powerchess/powerchess/vista/imagenes/caballoNegro.png");
        put("Reina", "PowerChess/src/main/java/org/java/powerchess/powerchess/vista/imagenes/reinaNegra.png");
    }};

    public VistaCoronacion(Juego juego) {
        Text titulo = new Text("Elegir una Pieza");
        titulo.setBoundsType(TextBoundsType.LOGICAL_VERTICAL_CENTER);
        titulo.setStrokeType(StrokeType.OUTSIDE);
        titulo.setTextAlignment(TextAlignment.CENTER);
        titulo.setTextOrigin(VPos.CENTER);
        titulo.setFont(new Font("System Bold", 12));

        gridPiezas = new GridPane();
        gridPiezas.setPrefHeight(40);
        gridPiezas.setPrefWidth(120);

        imageViews = new ArrayList<>();
        for ( int i = 0 ; i < 4 ; i++ ) {
            try {
                //InputStream piezaIS = new FileInputStream(imgPiezasPosibles.get("TorreB"));
                //Image piezaCoronacion = new Image(piezaIS);
                ImageView imageView = new ImageView();
                imageView.setFitHeight(35);
                imageView.setFitWidth(35);

                imageViews.add(imageView);
                gridPiezas.add(imageView,i,0);
            } catch (Exception e) {

            }

        }
        gridPiezas.setAlignment(Pos.CENTER);
        setAlignment(Pos.CENTER);

        this.getChildren().add(titulo);
        this.getChildren().add(gridPiezas);

        ControladorMouseCoronacion eventHandler = new ControladorMouseCoronacion(imageViews);
    }


    @Override
    public void update(Observable observable, Object o) {
        // Si hay coronacion, setImage
        // esto deberia hacerse considerando el valor de panelVisible
        this.setVisible(true);

        for ( ImageView image : imageViews ) {
            image.setImage(null);
        }

        // TODO: cambiar por un if
        HashMap<String, String> piezasACargar = imgPiezasBlancas;

        try{
            InputStream piezaISTorre = new FileInputStream(piezasACargar.get("Torre"));
            Image piezaTorre = new Image(piezaISTorre);
            imageViews.getFirst().setImage(piezaTorre);

            InputStream piezaISAlfil = new FileInputStream(piezasACargar.get("Alfil"));
            Image piezaAlfil = new Image(piezaISAlfil);
            imageViews.get(1).setImage(piezaAlfil);

            InputStream piezaISCaballo = new FileInputStream(piezasACargar.get("Caballo"));
            Image piezaCaballo = new Image(piezaISCaballo);
            imageViews.get(2).setImage(piezaCaballo);

            InputStream piezaISReina = new FileInputStream(piezasACargar.get("Reina"));
            Image piezaReina = new Image(piezaISReina);
            imageViews.get(3).setImage(piezaReina);

        } catch (Exception e) {

        }

    }
}
