package org.java.powerchess.powerchess;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.java.powerchess.powerchess.controlador.ControladorJuego;
import org.java.powerchess.powerchess.vista.VistaCasilla;
import org.java.powerchess.powerchess.vista.VistaCoronacion;
import org.java.powerchess.powerchess.vista.VistaFinPartida;
import org.java.powerchess.powerchess.vista.VistaJugador;
import org.java.powerchess.powerchess.vista.VistaPoder;

import java.io.IOException;
/**
 * JavaFX App
 */
public class App extends Application {
  private Stage primaryStage;
  private final int cantidad_de_filas = 8;
  private final int cantidad_de_columnas = 8;

  @Override
  public void start(Stage primaryStage) throws IOException {
    FXMLLoader loader = new FXMLLoader(App.class.getResource("powerchess.fxml"));
    Parent root = loader.load();
    this.primaryStage = primaryStage;

    Scene scene = new Scene(root, 700, 700);

    primaryStage.setScene(scene);
    primaryStage.show();

    // TODO: Hardcodeado
    Juego juego = new Juego(new Jugador("jugador blanco", Color.BLANCO), new Jugador("jugador negro", Color.NEGRO));

    // Busca el id del gridPane en el FXML
    GridPane gridPaneTablero = (GridPane) root.lookup("#gridPaneTablero");

    VBox vBoxHabilidades = (VBox) root.lookup("#vBoxHabilidades");
    VBox vBoxCoronacion = (VBox) root.lookup("#vBoxCoronacion");

    Button btnTablas = (Button) root.lookup("#buttonProponerTablas");

    EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
      public void handle(ActionEvent event) {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        juego.ofrecerTablas();
        VBox dialogVbox = new VBox(25);
        dialogVbox.setAlignment(Pos.CENTER);

        HBox actionsHbox = new HBox(25);
        actionsHbox.setAlignment(Pos.CENTER);

        Button aceptar = new Button("aceptar");
        Button rechazar = new Button("rechazar");
        rechazar.setOnAction(new EventHandler<ActionEvent>() {
          public void handle(ActionEvent event) {
            juego.responderTablas(false);
            dialog.close();
          }
        });

        aceptar.setOnAction(new EventHandler<ActionEvent>() {
          public void handle(ActionEvent event) {
            juego.responderTablas(true);
            dialog.close();
          }
        });
        actionsHbox.getChildren().addAll(aceptar, rechazar);
        dialogVbox.getChildren().addAll(
            new Text("El jugador " + juego.obtenerJugadorActual().getNombre() + " propone tablas: "), actionsHbox);

        Scene dialogScene = new Scene(dialogVbox, 300, 200);

        dialog.setScene(dialogScene);
        dialog.show();
      }
    };

    // when button is pressed
    btnTablas.setOnAction(event);

    Button btnRendirse = (Button) root.lookup("#buttonRendirse");
    btnRendirse.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent event) {
        juego.rendirse();
      }
    });

    cargarTablero(juego, gridPaneTablero);
    cargarHabilidades(juego, vBoxHabilidades);
    cargarCajaDeCoronacion(juego, vBoxCoronacion);
    cargarPantallaFinal(juego, primaryStage);
    cargarJugadorActual(juego, root);
  }

  public void cargarTablero(Juego juego, GridPane gridPaneTablero) {

    ControladorJuego controladorJuego = new ControladorJuego(juego);

    for (int fila = 0; fila < cantidad_de_filas; fila++) {
      for (int columna = 0; columna < cantidad_de_columnas; columna++) {
        double width = gridPaneTablero.getCellBounds(columna, fila).getWidth();
        double height = gridPaneTablero.getCellBounds(columna, fila).getHeight();
        Pair<Integer, Integer> casillaActual = new Pair(fila, columna);
        VistaCasilla casilla = new VistaCasilla(juego, casillaActual, width, height);
        controladorJuego.agregarObservable(juego, casilla);
        gridPaneTablero.add(casilla, fila, columna);
      }
    }

  }

  public void cargarHabilidades(Juego juego, VBox vBoxHabilidades) {
    ControladorJuego controladorJuego = new ControladorJuego(juego);
    VistaPoder vistaPoderes = new VistaPoder(juego);
    controladorJuego.agregarObservable(juego, vistaPoderes);
    vBoxHabilidades.getChildren().addAll(vistaPoderes);
  }

  public void cargarCajaDeCoronacion(Juego juego, VBox vBoxCoronacion) {
    ControladorJuego controladorJuego = new ControladorJuego(juego);
    VistaCoronacion vistaCoronacion = new VistaCoronacion(juego);
    controladorJuego.agregarObservable(juego, vistaCoronacion);
    vBoxCoronacion.getChildren().addAll(vistaCoronacion);
    vBoxCoronacion.setVisible(true);
  }

  public void cargarPantallaFinal(Juego juego, Stage primaryStage) throws IOException {
    ControladorJuego controladorJuego = new ControladorJuego(juego);

    FXMLLoader loaderFinal = new FXMLLoader(App.class.getResource("pantallaFinal.fxml"));

    VistaFinPartida vistaFinPartida = new VistaFinPartida(juego, loaderFinal, primaryStage);
    controladorJuego.agregarObservable(juego, vistaFinPartida);
  }

  public void cargarJugadorActual(Juego juego, Parent root) throws IOException {
    ControladorJuego controladorJuego = new ControladorJuego(juego);
    HBox hBoxJugadorBlanco = (HBox) root.lookup("#hBoxJugadorBlanco");
    HBox hBoxJugadorNegro = (HBox) root.lookup("#hBoxJugadorNegro");    
    Text textJugador = (Text) root.lookup("#PlayerName");

    VistaJugador vistaJugador = new VistaJugador(juego, hBoxJugadorBlanco, hBoxJugadorNegro,textJugador);
    controladorJuego.agregarObservable(juego, vistaJugador);
  }

  public static void main(String[] args) {
    launch();
  }

}
