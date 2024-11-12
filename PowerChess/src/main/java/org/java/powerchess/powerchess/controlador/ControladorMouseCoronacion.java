package org.java.powerchess.powerchess.controlador;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;
import org.java.powerchess.powerchess.*;
import org.java.powerchess.powerchess.vista.VistaCoronacion;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ControladorMouseCoronacion {
    List<ImageView> imageViews;
    private Juego juego;
    Boolean panelVisible;

    public ControladorMouseCoronacion(Juego juego, List<ImageView> imageViews, Boolean panelVisible) {
        this.imageViews = imageViews;
        for ( ImageView image : imageViews ) {
            image.setOnMouseClicked(this::handleImageViewClicked);
        }
        this.juego = juego;
        this.panelVisible = panelVisible;
    }

    private void handleImageViewClicked(MouseEvent event) {
        Tablero tablero = juego.obtenerTablero();
        Pair<Integer, Integer> casilla = tablero.obtenerCasillaSeleccionada();
        int x = casilla.getKey();
        int y = casilla.getValue();

        Color colorDelJugadorActual = juego.obtenerColorDelJugadorActual();

        Pieza nuevaPieza = obtenerPiezaSeleccionada(event, colorDelJugadorActual);

        if (nuevaPieza != null) {
            tablero.coronar(x, y, nuevaPieza);
            panelVisible = false;
            juego.cambiarTurno();
        }

    }

    @Nullable
    private Pieza obtenerPiezaSeleccionada(MouseEvent event, Color colorDelJugadorActual) {
        ImageView clickedImageView = (ImageView) event.getSource();

        Pieza nuevaPieza = null;

        if (clickedImageView == imageViews.get(0)) {
            nuevaPieza = new Torre(colorDelJugadorActual);
        } else if (clickedImageView == imageViews.get(1)) {
            nuevaPieza = new Alfil(colorDelJugadorActual);
        } else if (clickedImageView == imageViews.get(2)) {
            nuevaPieza = new Caballo(colorDelJugadorActual);
        } else if (clickedImageView == imageViews.get(3)) {
            nuevaPieza = new Reina(colorDelJugadorActual);
        } else {
            // El jugador hizo click en otra parte, no hacer nada
        }
        return nuevaPieza;
    }

}
