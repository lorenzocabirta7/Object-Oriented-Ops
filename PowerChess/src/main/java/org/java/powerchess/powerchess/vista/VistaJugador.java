package org.java.powerchess.powerchess.vista;

import javafx.scene.text.Text;
import javafx.scene.layout.HBox;

import org.java.powerchess.powerchess.Color;
import org.java.powerchess.powerchess.Juego;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class VistaJugador implements Observer {
  private Juego juego;
  private HBox hBoxJugadorBlanco;
  private HBox hBoxJugadorNegro;
  private Text textoJugador;

  public VistaJugador(Juego juego,HBox hBoxJugadorBlanco,HBox hBoxJugadorNegro,Text textoJugador ) throws IOException {
    super();
    this.juego = juego;
    this.hBoxJugadorBlanco = hBoxJugadorBlanco;
    this.hBoxJugadorNegro = hBoxJugadorNegro;
    this.textoJugador = textoJugador;

    this.juego.addObserver(this);

    textoJugador.setText(juego.obtenerJugadorActual().getNombre());
    if (juego.obtenerColorDelJugadorActual() == Color.BLANCO) {
      hBoxJugadorBlanco.setStyle(" -fx-background-color: white;");
      hBoxJugadorNegro.setStyle("");
    } else {
      hBoxJugadorBlanco.setStyle("");
      hBoxJugadorNegro.setStyle(" -fx-background-color: white;");
    }

  }

  @Override
  public void update(Observable observable, Object o) {
    textoJugador.setText(juego.obtenerJugadorActual().getNombre());
    if (juego.obtenerColorDelJugadorActual() == Color.BLANCO) {
      hBoxJugadorBlanco.setStyle(" -fx-background-color: white;");
      hBoxJugadorNegro.setStyle("");
    } else {
      hBoxJugadorBlanco.setStyle("");
      hBoxJugadorNegro.setStyle(" -fx-background-color: white;");
    }

  }
}
