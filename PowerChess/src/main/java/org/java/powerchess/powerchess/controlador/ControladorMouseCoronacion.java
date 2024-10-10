package org.java.powerchess.powerchess.controlador;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;
import org.java.powerchess.powerchess.Juego;

import java.util.List;

public class ControladorMouseCoronacion {
    List<ImageView> imageViews;
    public ControladorMouseCoronacion(List<ImageView> imageViews) {
        this.imageViews = imageViews;
        for ( ImageView image : imageViews ) {
            image.setOnMouseClicked(this::handleImageViewDragged);
        }
    }

    private void handleImageViewDragged(MouseEvent event) {
        ImageView clickedImageView = (ImageView) event.getSource();

        if (clickedImageView == imageViews.get(0)) {
            System.out.println("Torre");
        } else if (clickedImageView == imageViews.get(1)) {
            System.out.println("Alfil");
        } else if (clickedImageView == imageViews.get(2)) {
            System.out.println("Caballo");
        } else if (clickedImageView == imageViews.get(3)) {
            System.out.println("Reina");
        }
    }

}
