package org.java.powerchess.powerchess;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.java.powerchess.powerchess.vista.VistaCasilla;

import java.io.IOException;
/**
 * JavaFX App
 */
public class App extends Application {

    private final int cantidad_de_filas = 8;
    private final int cantidad_de_columnas = 8;

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("powerchess.fxml"));
        Parent root = loader.load();


        Scene scene = new Scene(root,700,700);

        primaryStage.setScene(scene);
        primaryStage.show();

        // TODO: Hardcodeado
        Juego juego = new Juego(new Jugador("A", Color.BLANCO), new Jugador("B", Color.NEGRO));

        // Busca el id del gridPane en el FXML
        GridPane gridPaneTablero = (GridPane) root.lookup("#gridPaneTablero");

        cargarTablero(juego, gridPaneTablero);

    }

    public void cargarTablero(Juego juego, GridPane gridPaneTablero) {

        Tablero tablero = juego.obtenerTablero();

        for (int fila = 0 ; fila < cantidad_de_filas ; fila++) {
            for (int columna = 0 ; columna < cantidad_de_columnas ; columna++) {
                double width = gridPaneTablero.getCellBounds(columna, fila).getWidth();
                double height = gridPaneTablero.getCellBounds(columna, fila).getHeight();

                VistaCasilla casilla = new VistaCasilla(tablero.obtenerPieza(fila, columna), width, height);
                gridPaneTablero.add(casilla, fila, columna);
            }
        }

    }

    public static void main(String[] args) {
        launch();
    }

}
