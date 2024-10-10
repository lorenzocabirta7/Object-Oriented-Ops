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
import org.java.powerchess.powerchess.controlador.ControladorMouseCasilla;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class VistaCasilla extends StackPane implements Observer {
    private Juego juego;
    private Pair<Integer, Integer> casillaActual;
    ImageView piezaImgView;
    ImageView efectoImgView;
    private double anchoCeldaGridPane;
    private double alturaCeldaGridPane;
    private boolean huboMovimiento = false;

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

    private final HashMap<String, String> imgEfecto = new HashMap<>() {{
      put("Freeze", "PowerChess/src/main/java/org/java/powerchess/powerchess/vista/imagenes/freeze.png");
      put("Shield", "PowerChess/src/main/java/org/java/powerchess/powerchess/vista/imagenes/freeze.png");
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
            pieza.addObserver(this);
            cargarImagenDePieza(pieza);
        }

        ControladorMouseCasilla eventHandler = new ControladorMouseCasilla(this.juego, this.casillaActual);
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
    }

    @Override
    public void update(Observable observable, Object o) {
        Tablero tablero = this.juego.obtenerTablero();
        Pieza pieza = tablero.obtenerPieza(casillaActual.getKey(), this.casillaActual.getValue());
        
        // TODO: agregar un chequeo aca respecto al color de la pieza
        if ( tablero.casillaEstaSeleccionada(this.casillaActual) && pieza != null) {
          
            this.setBackground(Background.fill(new javafx.scene.paint.Color(0,0,1,0.2)));
        }
        else {
            this.setBackground(null);
        }

        cargarImagenDePieza(pieza);

    }

    private void cargarImagenDePieza(Pieza pieza) {
        if ( pieza == null ) {
            if (piezaImgView != null){
                piezaImgView.setImage(null);
            }

            if (efectoImgView != null){
              efectoImgView.setImage(null);
            }
           

            return;
        }
        String color = ( pieza.getColor() == Color.BLANCO ) ? "B":"N";
        try {
            getChildren().clear();
            InputStream piezaIS = new FileInputStream(imgPieza.get(pieza.getNombre() + color));
            Image piezaCasilla = new Image(piezaIS);

            if ( piezaImgView == null ) {
                piezaImgView = new ImageView(piezaCasilla);
                piezaImgView.setFitWidth(anchoCeldaGridPane);
                piezaImgView.setFitHeight(alturaCeldaGridPane);
                getChildren().add(piezaImgView);
            } else {
                piezaImgView.setImage(piezaCasilla);
                getChildren().add(piezaImgView);
            }
          

          if ( pieza.estaCongelada() ) {
            Image imageFreeze = new Image(new FileInputStream(imgEfecto.get("Freeze")));
            efectoImgView = new ImageView(imageFreeze);
            efectoImgView.setFitWidth(anchoCeldaGridPane);
            efectoImgView.setFitHeight(alturaCeldaGridPane);
            getChildren().add(efectoImgView);
          }

          if ( pieza.estaProtegida() ) {
            Image imageFreeze = new Image(new FileInputStream(imgEfecto.get("Shield")));
            efectoImgView = new ImageView(imageFreeze);
            efectoImgView.setFitWidth(anchoCeldaGridPane);
            efectoImgView.setFitHeight(alturaCeldaGridPane);
            getChildren().add(efectoImgView);
          }
          
            

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
