package org.java.powerchess.powerchess.vista;

import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;
import org.java.powerchess.powerchess.Color;
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
    private Juego juego;
    private Text titulo;

    private final HashMap<String, String> imgPiezasBlancas = new HashMap<>() {{
        put("Torre", "src/main/java/org/java/powerchess/powerchess/vista/imagenes/torreBlanca.png");
        put("Alfil", "src/main/java/org/java/powerchess/powerchess/vista/imagenes/alfilBlanco.png");
        put("Caballo", "src/main/java/org/java/powerchess/powerchess/vista/imagenes/caballoBlanco.png");
        put("Reina", "src/main/java/org/java/powerchess/powerchess/vista/imagenes/reinaBlanca.png");
    }};

    private final HashMap<String, String> imgPiezasNegras = new HashMap<>() {{
        put("Torre", "src/main/java/org/java/powerchess/powerchess/vista/imagenes/torreNegra.png");
        put("Alfil", "src/main/java/org/java/powerchess/powerchess/vista/imagenes/alfilNegro.png");
        put("Caballo", "src/main/java/org/java/powerchess/powerchess/vista/imagenes/caballoNegro.png");
        put("Reina", "src/main/java/org/java/powerchess/powerchess/vista/imagenes/reinaNegra.png");
    }};

    public VistaCoronacion(Juego juego) {
        this.juego = juego;

        this.titulo = new Text("Elegir una Pieza");
        this.titulo.setBoundsType(TextBoundsType.LOGICAL_VERTICAL_CENTER);
        this.titulo.setStrokeType(StrokeType.OUTSIDE);
        this.titulo.setTextAlignment(TextAlignment.CENTER);
        this.titulo.setTextOrigin(VPos.CENTER);
        this.titulo.setFont(new Font("System Bold", 12));
        this.titulo.setVisible(false);

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

        this.getChildren().add(this.titulo);
        this.getChildren().add(gridPiezas);

        juego.addObserver(this);
        ControladorMouseCoronacion eventHandler = new ControladorMouseCoronacion(juego, imageViews, panelVisible);
    }


    @Override
    public void update(Observable observable, Object o) {
        Boolean hayCoronacion = false;
        if (o != null) {
            hayCoronacion = (Boolean) o;
        }

        if ( hayCoronacion ) {
            for ( ImageView image : imageViews ) {
                image.setImage(null);
            }

            this.titulo.setVisible(true);
            this.setVisible(true);
            panelVisible = true;

            HashMap<String, String> piezasACargar = (this.juego.obtenerJugadorActual().getColor() == Color.BLANCO) ? imgPiezasBlancas : imgPiezasNegras;

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
        } else {
            this.setVisible(false);
            this.titulo.setVisible(false);
        }






    }
}
