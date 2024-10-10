package org.java.powerchess.powerchess;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.java.powerchess.powerchess.controlador.ControladorJuego;
import org.java.powerchess.powerchess.vista.VistaCasilla;
import org.java.powerchess.powerchess.vista.VistaCoronacion;
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

        Scene scene = new Scene(root,700,700);

        primaryStage.setScene(scene);
        primaryStage.show();

        // TODO: Hardcodeado
        Juego juego = new Juego(new Jugador("A", Color.BLANCO), new Jugador("B", Color.NEGRO));

        // Busca el id del gridPane en el FXML
        GridPane gridPaneTablero = (GridPane) root.lookup("#gridPaneTablero");

        VBox vBoxHabilidades = (VBox) root.lookup("#vBoxHabilidades");
        VBox vBoxCoronacion = (VBox) root.lookup("#vBoxCoronacion");

        cargarTablero(juego, gridPaneTablero);
        cargarHabilidades(juego, vBoxHabilidades);
        cargarCajaDeCoronacion(juego, vBoxCoronacion);
    }

    public void cargarTablero(Juego juego, GridPane gridPaneTablero) {

        ControladorJuego controladorJuego = new ControladorJuego(juego);

        for (int fila = 0 ; fila < cantidad_de_filas ; fila++) {
            for (int columna = 0 ; columna < cantidad_de_columnas ; columna++) {
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

    public static void main(String[] args) {
        launch();
    }

}
