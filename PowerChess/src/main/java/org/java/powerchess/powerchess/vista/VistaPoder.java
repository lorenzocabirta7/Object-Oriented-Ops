package org.java.powerchess.powerchess.vista;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.java.powerchess.powerchess.Juego;
import org.java.powerchess.powerchess.Jugador;
import org.java.powerchess.powerchess.Poder;
import org.java.powerchess.powerchess.controlador.ControladorMousePoderes;

import java.util.Observable;
import java.util.Observer;

public class VistaPoder extends VBox implements Observer {
    private Juego juego;
    ImageView poderImgView;

    public VistaPoder(Juego juego) {
        super();
        this.juego = juego;
        this.juego.addObserver(this);

        Jugador jugadorActual = this.juego.obtenerJugadorActual();
        jugadorActual.addObserver(this);
        if (!jugadorActual.getPoderes().isEmpty() ) {
            for( int i=0; i < jugadorActual.getPoderes().size(); i++) {
              
              Poder poder = jugadorActual.getPoderes().get(i);
              if(poder.habilitado()){
                StackPane textPoder = new StackPane(new Text( poder.getNombre() + " de " + jugadorActual.getNombre()));
                ControladorMousePoderes eventHandler = new ControladorMousePoderes(this.juego, poder);
                textPoder.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
                getChildren().add(textPoder );
              }
            }
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        Jugador jugadorActual = this.juego.obtenerJugadorActual();
        jugadorActual.addObserver(this);
        
        this.getChildren().clear();
        if (!jugadorActual.getPoderes().isEmpty() ) {
            
            for( int i=0; i < jugadorActual.getPoderes().size(); i++) {

              Poder poder = jugadorActual.getPoderes().get(i);

              if(poder.habilitado()){
                StackPane textPoder = new StackPane(new Text(poder.getNombre() + " de " + jugadorActual.getNombre()));
                if ( jugadorActual.poderEstaSeleccionada(poder)) {
                  textPoder.setBackground(Background.fill(new javafx.scene.paint.Color(0,0,1,0.2)));
                }

                ControladorMousePoderes eventHandler = new ControladorMousePoderes(this.juego, poder);
                textPoder.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
                this.getChildren().add( textPoder);
              }
            }
        }

    }
}
