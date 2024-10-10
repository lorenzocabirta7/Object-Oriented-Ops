package org.java.powerchess.powerchess.vista;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Scene;

import org.java.powerchess.powerchess.Juego;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class VistaFinPartida implements Observer {
  private Juego juego;
  private Parent rootFinal;
  private Stage stage;

  public VistaFinPartida(Juego juego, FXMLLoader loaderFinal, Stage stage) throws IOException {
    super();
    this.juego = juego;
    this.juego.addObserver(this);
    this.rootFinal = loaderFinal.load();
    this.stage = stage;
  }

  @Override
  public void update(Observable observable, Object o) {
    if (juego.partidaTerminada()) {
      Scene scene = new Scene(rootFinal, 700, 700);
      Text resultadoJuego = (Text) rootFinal.lookup("#resultadoJuego");
      if(juego.getJugadorGanador() != null){
        resultadoJuego.setText("Partida ganada por el jugador " + juego.getJugadorGanador().getNombre());
      }else{
        resultadoJuego.setText("La partida termina en tablas");
      }

      stage.setScene(scene);
      stage.show();

    }

  }

}
